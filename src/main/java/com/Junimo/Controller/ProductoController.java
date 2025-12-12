package com.Junimo.Controller;

import com.Junimo.Entity.Producto;
import com.Junimo.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {
    
    @Autowired
    private ProductoService service;
    
    // Mismo método de validación que en los otros controllers
    private boolean validarApiKey(String apiKey, String... rolesPermitidos) {
        // Mapa de API Keys -> Tipo de usuario
        Map<String, String> keys = new HashMap<>();
        keys.put("admin", "Administrador");
        keys.put("vendedor", "Vendedor"); 
        keys.put("cliente", "Cliente");
        
        // Verificar si la key existe
        String rol = keys.get(apiKey);
        if (rol == null) {
            return false;
        }
        
        // Si no se especifican roles, cualquier key válida sirve
        if (rolesPermitidos.length == 0) {
            return true;
        }
        
        // Verificar si el rol está permitido
        for (String rolPermitido : rolesPermitidos) {
            if (rolPermitido.equals(rol)) {
                return true;
            }
        }
        
        return false;
    }
    
    // ========== MÉTODOS PÚBLICOS (SIN API KEY) ==========
    
    @GetMapping("/productos")
    public ResponseEntity<?> findAllProductos() {
        // Este endpoint NO requiere autenticación
        try {
            List<Producto> productos = service.getProductos();
            if (productos.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay productos registrados en el sistema");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener la lista de productos: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/productoById/{codigo}")
    public ResponseEntity<?> findProductoById(@PathVariable String codigo) {
        // Este endpoint NO requiere autenticación
        try {
            Producto producto = service.getProductoById(codigo);
            if (producto == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Producto con código " + codigo + " no encontrado");
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/productoByName/{nombre}")
    public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
        // Este endpoint NO requiere autenticación
        try {
            Producto producto = service.getProductoByNombre(nombre);
            if (producto == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Producto con nombre '" + nombre + "' no encontrado");
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar producto por nombre: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/productosByCategoria/{categoriaId}")
    public ResponseEntity<?> findProductosByCategoria(@PathVariable int categoriaId) {
        // Este endpoint NO requiere autenticación
        try {
            List<Producto> productos = service.getProductosByCategoria(categoriaId);
            if (productos.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay productos registrados para la categoría ID: " + categoriaId);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar productos por categoría: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // ========== MÉTODOS PROTEGIDOS ==========
    
    @GetMapping("/productosStockCritico")
    public ResponseEntity<?> findProductosStockCritico(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin y Vendedor pueden ver stock crítico
        if (!validarApiKey(apiKey, "Administrador", "Vendedor")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador o Vendedor requerida"));
        }
        
        try {
            List<Producto> productos = service.getProductosStockCritico();
            if (productos.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay productos con stock crítico");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener productos con stock crítico: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/productosByPrecio")
    public ResponseEntity<?> findProductosByPrecioBetween(
            @RequestParam double precioMin, 
            @RequestParam double precioMax,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin y Vendedor pueden buscar por rango de precio
        if (!validarApiKey(apiKey, "Administrador", "Vendedor")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador o Vendedor requerida"));
        }
        
        try {
            List<Producto> productos = service.getProductosByPrecioBetween(precioMin, precioMax);
            if (productos.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay productos en el rango de precio $" + precioMin + " - $" + precioMax);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar productos por precio: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/addProducto")
    public ResponseEntity<?> addProducto(
            @RequestBody Producto producto,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede crear productos
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Producto productoGuardado = service.saveProducto(producto);
            return ResponseEntity.ok(productoGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al crear el producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/updateProducto")
    public ResponseEntity<?> updateProducto(
            @RequestBody Producto producto,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede actualizar productos
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Producto productoActualizado = service.updateProducto(producto);
            if (productoActualizado == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No se puede actualizar. Producto con código " + producto.getCodigo() + " no existe");
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al actualizar producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/deleteProducto/{codigo}")
    public ResponseEntity<?> deleteProducto(
            @PathVariable String codigo,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede eliminar productos
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            String resultado = service.deleteProducto(codigo);
            Map<String, String> response = new HashMap<>();
            if (resultado.contains("no encontrado")) {
                response.put("mensaje", resultado);
                return ResponseEntity.status(404).body(response);
            }
            response.put("mensaje", resultado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al eliminar producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Error en el servidor: " + e.getMessage());
        return ResponseEntity.status(500).body(response);
    }
}