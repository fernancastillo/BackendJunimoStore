package com.Junimo.Repository;

import com.Junimo.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 *
 * @author scarleth
 */

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    

    public Categoria findByNombre(String nombre);
    

    public List<Categoria> findByCategoriaPadreIsNull();
    

    public List<Categoria> findByCategoriaPadreId(int idPadre);
    
   
    public boolean existsByNombre(String nombre);
}