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
@RequestMapping("/v1")
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
}

