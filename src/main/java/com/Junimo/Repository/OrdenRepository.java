package com.Junimo.Repository;

import com.Junimo.Entity.Orden;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fernando
 */
public interface OrdenRepository extends JpaRepository<Orden, String> {
    
    public List<Orden> findByUsuarioRun(int run);
    public List<Orden> findByEstadoEnvio(String tipo);
    public List<Orden> findByFecha(Date fecha); 
}