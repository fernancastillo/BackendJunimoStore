package com.Junimo.Repository;

import com.Junimo.Entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fernando
 */
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Integer> { 

    public DetalleOrden findDetalleByNombreProducto(String nombreProducto);
}
