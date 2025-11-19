package com.Junimo.Repository;

import com.Junimo.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 *
 * @author scarleth
 */


public interface ProductoRepository extends JpaRepository<Producto, String> {
    
  
    public Producto findByNombre(String nombre);
    
    
    public List<Producto> findByCategoriaId(int categoriaId);
    

    public List<Producto> findByStockActualLessThanEqual(int stockCritico);
    
   
    public List<Producto> findByNombreContainingIgnoreCase(String nombre);
    

    public List<Producto> findByPrecioBetween(double precioMin, double precioMax);
}