package com.Junimo.Service;

import com.Junimo.Entity.Categoria;
import com.Junimo.Entity.Producto;
import com.Junimo.Repository.CategoriaRepository;
import com.Junimo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *
 * @author scarleth
 */

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository repository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
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
    
    @Transactional
    public String deleteCategoria(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Categoría con ID " + id + " no existe");
        }
        List<Producto> productosAsociados = productoRepository.findByCategoriaId(id);
        
        if (!productosAsociados.isEmpty()) {
            throw new RuntimeException("No se puede eliminar la categoría. Existen " + 
                                     productosAsociados.size() + 
                                     " producto(s) asociado(s).");
        }
        repository.deleteById(id);
        return "Categoría ID: " + id + " eliminada con éxito!";
    }
    
    public Categoria updateCategoria(Categoria categoria) {
        Categoria existingCategoria = repository.findById(categoria.getId()).orElse(null);
        if (existingCategoria == null) {
            throw new RuntimeException("Categoría con ID " + categoria.getId() + 
                                     " no encontrada. No se puede actualizar.");
        }
        
        existingCategoria.setNombre(categoria.getNombre());
        return repository.save(existingCategoria);
    }
}