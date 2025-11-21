package com.Junimo.Controller;

import com.Junimo.Entity.Orden;
import com.Junimo.Service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Fernando
 */
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdenController {

    @Autowired
    private OrdenService service;

    @PostMapping("/addOrden")
    public Orden addOrden(@RequestBody Orden o) {
        return service.saveOrden(o);
    }

    @GetMapping("/ordenes")
    public List<Orden> getAllOrdenes() {
        return service.getOrdenes();
    }

    @GetMapping("/OrdenByNumero/{numero}")
    public Orden getOrden(@PathVariable String numero) {
        return service.getOrdenByNumero(numero);
    }

    @PutMapping("/updateOrden")
    public Orden updateOrden(@RequestBody Orden o) {
        return service.updateOrden(o);
    }

    // Nuevo endpoint específico para actualizar solo el estado
    @PutMapping("/updateOrdenEstado")
    public Orden updateOrdenEstado(@RequestBody Map<String, String> request) {
        String numeroOrden = request.get("numeroOrden");
        String nuevoEstado = request.get("estadoEnvio");
        
        if (numeroOrden == null || nuevoEstado == null) {
            throw new RuntimeException("Se requieren numeroOrden y estadoEnvio");
        }
        
        return service.updateOrdenEstado(numeroOrden, nuevoEstado);
    }

    @DeleteMapping("/deleteOrden/{numero}")
    public String deleteOrden(@PathVariable String numero) {
        return service.deleteOrden(numero);
    }
}