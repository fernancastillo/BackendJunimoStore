package com.Junimo.Repository;

import com.Junimo.Entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author scarleth
 */

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    

    public Categoria findByNombre(String nombre);
}