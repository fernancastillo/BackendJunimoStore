package com.Junimo.Controller;

import com.Junimo.Entity.DetalleOrden;
import com.Junimo.Service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class DetalleOrdenController {
    
    @Autowired
    private DetalleOrdenService service;
    
    // Mismo método de validación que en UsuarioController
    private boolean validarApiKey(String apiKey, String... rolesPermitidos) {
        // Mapa de API Keys -> Tipo de usuario (igual que en UsuarioController)
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
    
    // ========== MÉTODOS PROTEGIDOS ==========
    
    @GetMapping("/detallesOrdenes")
    public ResponseEntity<?> getDetalles(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Admin y Vendedor pueden ver todos los detalles
        if (!validarApiKey(apiKey, "Administrador", "Vendedor")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador o Vendedor requerida"));
        }
        
        try {
            List<DetalleOrden> detalles = service.getDetalles();
            if (detalles.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay detalles de órdenes registrados");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener detalles: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/detallesOrdenesById/{id}")
    public ResponseEntity<?> findDetalleOrdenById(
            @PathVariable int id,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Admin y Vendedor pueden ver detalles específicos
        if (!validarApiKey(apiKey, "Administrador", "Vendedor")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador o Vendedor requerida"));
        }
        
        try {
            DetalleOrden detalle = service.getDetalleById(id);
            if (detalle == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Detalle de orden con ID " + id + " no encontrado");
                return ResponseEntity.status(404).body(response);
            }
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar detalle: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/addDetalleOrden")
    public ResponseEntity<?> addDetalle(
            @RequestBody DetalleOrden d,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        // Solo Cliente puede agregar detalles de órdenes
        if (!validarApiKey(apiKey, "Cliente")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Cliente requerida"));
        }
        
        try {
            DetalleOrden detalleGuardado = service.saveDetalle(d);
            return ResponseEntity.ok(detalleGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
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