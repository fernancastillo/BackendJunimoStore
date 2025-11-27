package com.Junimo.Controller;

import com.Junimo.Entity.DetalleOrden;
import com.Junimo.Service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fernando
 */
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService service;

    @GetMapping("/detallesOrdenes")
    public ResponseEntity<?> getDetalles() {
        try {
            List<DetalleOrden> detalles = service.getDetalles();
            if (detalles.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay detalles de órdenes registrados en el sistema");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener la lista de detalles de órdenes: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/detallesOrdenesById/{id}")
    public ResponseEntity<?> findDetalleOrdenById(@PathVariable int id){
        try {
            DetalleOrden detalle = service.getDetalleById(id);
            if (detalle == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Detalle de orden con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar detalle de orden: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/addDetalleOrden")
    public ResponseEntity<?> addDetalle(@RequestBody DetalleOrden d) {
        try {
            DetalleOrden detalleGuardado = service.saveDetalle(d);
            // MANTENER 200 OK para compatibilidad con frontend existente
            return ResponseEntity.ok(detalleGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            // El mensaje de error mantiene el mismo formato
            response.put("mensaje", e.getMessage());
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