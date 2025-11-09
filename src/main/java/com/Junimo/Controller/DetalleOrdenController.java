package com.Junimo.Controller;

import com.Junimo.Entity.DetalleOrden;
import com.Junimo.Service.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author Fernando
 */
@RestController
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService service;

    @GetMapping("/detallesOrdenes")
    public List<DetalleOrden> getDetalles() {
        return service.getDetalles();
    }

    @GetMapping("/detallesOrdenesById/{id}")
    public DetalleOrden findDetalleOrdenById(@PathVariable int id){
        return service.getDetalleById(id);
    }

    @PostMapping("/addDetalleOrden")
    public DetalleOrden addDetalle(@RequestBody DetalleOrden d) {
        return service.saveDetalle(d);
    }

    @PutMapping("/update")
    public DetalleOrden updateDetalleOrden(@RequestBody DetalleOrden d){
        return service.updateDetalleOrden(d);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDetalle(@PathVariable int id) {
        return service.deleteDetalle(id);
    }

    @GetMapping("/detallesOrdenesByNombre/{nombre}")
    public DetalleOrden findDetalleByNombreDetalle(@PathVariable String nombre){
        return service.getDetalleByNombreDetalle(nombre);
    }
}

