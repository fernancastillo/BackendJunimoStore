package com.Junimo.Service;

import com.Junimo.Entity.Categoria;
import com.Junimo.Repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



/**
 *
 * @author scarleth
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
    
    public List<Categoria> getCategoriasPrincipales() {
        return repository.findByCategoriaPadreIsNull();
    }
    
    public List<Categoria> getSubcategorias(int padreId) {
        return repository.findByCategoriaPadreId(padreId);
    }
    
    public String deleteCategoria(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Categoría eliminada: " + id;
        } else {
            return "Categoría no encontrada: " + id;
        }
    }
    
    public Categoria updateCategoria(Categoria categoria) {
        Categoria existingCategoria = repository.findById(categoria.getId()).orElse(null);
        if (existingCategoria != null) {
            existingCategoria.setNombre(categoria.getNombre());
            existingCategoria.setCategoriaPadre(categoria.getCategoriaPadre());
            return repository.save(existingCategoria);
        }
        return null;
    }
}