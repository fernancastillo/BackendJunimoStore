package com.Junimo.Repository;

import com.Junimo.Entity.Orden;
import java.sql.Date;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fernando
 */
public interface OrdenRepository extends JpaRepository<Orden, String> {
    
    public Orden findByRun(int run);
    public Orden findByEstado(String tipo);
    public Orden findByFecha(Date fecha); 
}