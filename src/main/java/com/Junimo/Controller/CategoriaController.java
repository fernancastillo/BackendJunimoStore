package com.Junimo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Junimo.Entity.Categoria;
import com.Junimo.Service.CategoriaService;

/**
 *
 * @author scarleth
 */


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {
    
    @Autowired
    private CategoriaService service;
    
    @PostMapping("/addCategoria")
    public Categoria addCategoria(@RequestBody Categoria categoria){
        return service.saveCategoria(categoria);
    }
    
    @GetMapping("/categorias")
    public List<Categoria> findAllCategorias(){
        return service.getCategorias();
    }
    
    @GetMapping("/categoriaById/{id}")
    public Categoria findCategoriaById(@PathVariable int id){
        return service.getCategoriaById(id);
    }
    
    @GetMapping("/categoriaByName/{nombre}")
    public Categoria findByNombre(@PathVariable String nombre){
        return service.getCategoriaByNombre(nombre);
    }
}