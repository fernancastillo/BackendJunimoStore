package com.Junimo.Repository;

import com.Junimo.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fernando
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
    public Usuario findByNombre(String name);
    public Usuario findByCorreo(String correo);
    public Usuario findByTipo(String tipo);    
}