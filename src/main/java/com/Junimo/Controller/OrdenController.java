package com.Junimo.Controller;

import com.Junimo.Entity.Orden;
import com.Junimo.Service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdenController {

    @Autowired
    private OrdenService service;

    @PostMapping("/addOrden")
    public ResponseEntity<?> addOrden(@RequestBody Orden o) {
        try {
            if (o.getUsuario() == null) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "El usuario es requerido"));
            }
            
            if (o.getDetalles() == null || o.getDetalles().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "La orden debe tener al menos un producto"));
            }
            
            if (o.getTotal() <= 0) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "El total debe ser mayor a 0"));
            }
            
            Orden ordenGuardada = service.saveOrden(o);
            return ResponseEntity.ok(ordenGuardada);
            
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error interno del servidor al crear la orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/ordenes")
    public ResponseEntity<?> getAllOrdenes() {
        try {
            List<Orden> ordenes = service.getOrdenes();
            if (ordenes.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay órdenes registradas en el sistema");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener la lista de órdenes: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/OrdenByNumero/{numero}")
    public ResponseEntity<?> getOrden(@PathVariable String numero) {
        try {
            Orden orden = service.getOrdenByNumero(numero);
            if (orden == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Orden con número " + numero + " no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(orden);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar orden: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateOrden")
    public ResponseEntity<?> updateOrden(@RequestBody Orden o) {
        try {
            Orden ordenActualizada = service.updateOrden(o);
            return ResponseEntity.ok(ordenActualizada);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al actualizar la orden: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateOrdenEstado")
    public ResponseEntity<?> updateOrdenEstado(@RequestBody Map<String, String> request) {
        try {
            String numeroOrden = request.get("numeroOrden");
            String nuevoEstado = request.get("estadoEnvio");

            if (numeroOrden == null || nuevoEstado == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Se requieren numeroOrden y estadoEnvio");
                return ResponseEntity.badRequest().body(response);
            }

            Orden ordenActualizada = service.updateOrdenEstado(numeroOrden, nuevoEstado);
            return ResponseEntity.ok(ordenActualizada);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al actualizar el estado de la orden: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/deleteOrden/{numero}")
    public ResponseEntity<?> deleteOrden(@PathVariable String numero) {
        try {
            String resultado = service.deleteOrden(numero);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", resultado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al eliminar la orden: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/ordenesByUsuario/{run}")
    public ResponseEntity<?> getOrdenesByUsuario(@PathVariable int run) {
        try {
            List<Orden> ordenes = service.getOrdenByRun(run);
            if (ordenes.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay órdenes registradas para el usuario con RUN: " + run);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar órdenes por usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/ordenesByEstado/{estado}")
    public ResponseEntity<?> getOrdenesByEstado(@PathVariable String estado) {
        try {
            List<Orden> ordenes = service.getOrdenByEstado(estado);
            if (ordenes.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay órdenes con estado: " + estado);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar órdenes por estado: " + e.getMessage());
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