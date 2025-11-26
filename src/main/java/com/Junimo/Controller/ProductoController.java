package com.Junimo.Controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Junimo.Entity.Producto;
import com.Junimo.Service.ProductoService;

/**
 *
 * @author scarleth
 */

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {
    
    @Autowired
    private ProductoService service;
    
    @PostMapping("/addProducto")
    public ResponseEntity<?> addProducto(@RequestBody Producto producto){
        try {
            Producto productoGuardado = service.saveProducto(producto);
            return ResponseEntity.ok(productoGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al crear el producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/productos")
    public ResponseEntity<?> findAllProductos(){
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
    public ResponseEntity<?> findProductoById(@PathVariable String codigo){
        try {
            Producto producto = service.getProductoById(codigo);
            if (producto == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Producto con código " + codigo + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/productoByName/{nombre}")
    public ResponseEntity<?> findByNombre(@PathVariable String nombre){
        try {
            Producto producto = service.getProductoByNombre(nombre);
            if (producto == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Producto con nombre '" + nombre + "' no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar producto por nombre: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/productosByCategoria/{categoriaId}")
    public ResponseEntity<?> findProductosByCategoria(@PathVariable int categoriaId){
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
        
    @GetMapping("/productosStockCritico")
    public ResponseEntity<?> findProductosStockCritico(){
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
            @RequestParam double precioMax){
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
    
    @DeleteMapping("/deleteProducto/{codigo}")
    public ResponseEntity<?> deleteProducto(@PathVariable String codigo){
        try {
            String resultado = service.deleteProducto(codigo);
            Map<String, String> response = new HashMap<>();
            if (resultado.contains("no encontrado")) {
                response.put("mensaje", resultado);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            response.put("mensaje", resultado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al eliminar producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/updateProducto")
    public ResponseEntity<?> updateProducto(@RequestBody Producto producto){
        try {
            Producto productoActualizado = service.updateProducto(producto);
            if (productoActualizado == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No se puede actualizar. Producto con código " + producto.getCodigo() + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al actualizar producto: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Error en el servidor: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}