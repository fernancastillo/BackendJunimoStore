package com.Junimo.Service;

import com.Junimo.Entity.Categoria;
import com.Junimo.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author Fernando
 */
@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository repository;
    
    public Categoria saveCategoria(Categoria categoria) {
        return repository.save(categoria);
    }
    
    public List<Categoria> getCategorias() {
        return repository.findAll();
    }
    
    public Categoria getCategoriaById(int id) {
        return repository.findById(id).orElse(null);
    }
    
    public Categoria getCategoriaByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
    
}