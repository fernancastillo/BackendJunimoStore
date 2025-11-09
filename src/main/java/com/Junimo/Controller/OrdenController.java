package com.Junimo.Controller;

import com.Junimo.Entity.Orden;
import com.Junimo.Service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author Fernando
 */
@RestController

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

    @PutMapping("/update")
    public Orden updateOrden(@RequestBody Orden o) {
        return service.updateOrden(o);
    }

    @DeleteMapping("/delete/{numero}")
    public String deleteOrden(@PathVariable String numero) {
        return service.deleteOrden(numero);
    }
}