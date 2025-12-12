package com.Junimo.Controller;

import com.Junimo.Entity.Categoria;
import com.Junimo.Service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {
    
    @Autowired
    private CategoriaService service;
    
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
    
    // ========== MÉTODO PÚBLICO (SIN API KEY) ==========
    
    @GetMapping("/categorias")
    public ResponseEntity<?> findAllCategorias() {
        // Este endpoint NO requiere autenticación
        try {
            List<Categoria> categorias = service.getCategorias();
            if (categorias.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay categorías registradas en el sistema");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener la lista de categorías: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // ========== MÉTODOS PROTEGIDOS (SÓLO ADMIN) ==========
    
    @GetMapping("/categoriaById/{id}")
    public ResponseEntity<?> findCategoriaById(
            @PathVariable int id,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede ver categorías por ID
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Categoria categoria = service.getCategoriaById(id);
            if (categoria == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Categoría con ID " + id + " no encontrada");
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar categoría: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/categoriaByName/{nombre}")
    public ResponseEntity<?> findByNombre(
            @PathVariable String nombre,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede buscar categorías por nombre
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Categoria categoria = service.getCategoriaByNombre(nombre);
            if (categoria == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Categoría con nombre '" + nombre + "' no encontrada");
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar categoría por nombre: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/addCategoria")
    public ResponseEntity<?> addCategoria(
            @RequestBody Categoria categoria,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede crear categorías
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Categoria categoriaGuardada = service.saveCategoria(categoria);
            return ResponseEntity.ok(categoriaGuardada);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al crear la categoría: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PutMapping("/updateCategoria")
    public ResponseEntity<?> updateCategoria(
            @RequestBody Categoria categoria,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede actualizar categorías
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Categoria categoriaActualizada = service.updateCategoria(categoria);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al actualizar categoría: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/deleteCategoria/{id}")
    public ResponseEntity<?> deleteCategoria(
            @PathVariable int id,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Admin puede eliminar categorías
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Categoria categoriaExistente = service.getCategoriaById(id);
            if (categoriaExistente == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No se puede eliminar. Categoría con ID " + id + " no existe");
                return ResponseEntity.status(404).body(response);
            }

            String resultado = service.deleteCategoria(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", resultado);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al eliminar categoría: " + e.getMessage());
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