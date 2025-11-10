package com.Junimo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api")
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
    
    @GetMapping("/categoriasPrincipales")
    public List<Categoria> findCategoriasPrincipales(){
        return service.getCategoriasPrincipales();
    }
    
    @GetMapping("/subcategorias/{padreId}")
    public List<Categoria> findSubcategorias(@PathVariable int padreId){
        return service.getSubcategorias(padreId);
    }
    
    @DeleteMapping("/deleteCategoria/{id}")
    public String deleteCategoria(@PathVariable int id){
        return service.deleteCategoria(id);
    }
    
    @PutMapping("/updateCategoria")
    public Categoria updateCategoria(@RequestBody Categoria categoria){
        return service.updateCategoria(categoria);
    }
}