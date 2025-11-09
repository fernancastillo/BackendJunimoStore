package com.Junimo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Junimo.Entity.Usuario;
import com.Junimo.Repository.UsuarioRepository;

/**
 *
 * @author Fernando
 */

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repository;

    public Usuario saveUsuario(Usuario u){
        return repository.save(u);
    }
    
    public List<Usuario> getUsuarios(){
        return repository.findAll();
    }
    
    public Usuario getUsuarioById(int uRun){
        return repository.findById(uRun).orElse(null);
    }
    
    public String deleteUsuario(int run){
        repository.deleteById(run);
        return "Usuario RUN: " + run + " eliminado con éxito!";
    }
    
    public Usuario updateUsuario(Usuario u){
        Usuario existingUsuario = repository.findById(u.getRun() ).orElse(null);
        if (existingUsuario == null) {
        throw new RuntimeException("Usuario con RUN " + u.getRun() + " no encontrado.");
        }
        existingUsuario.setNombre( u.getNombre() );
        existingUsuario.setApellidos( u.getApellidos() );
        existingUsuario.setCorreo( u.getCorreo() );
        existingUsuario.setDireccion( u.getDireccion() );
        existingUsuario.setFechaNac( u.getFechaNac() );
        existingUsuario.setRegion( u.getRegion() );
        existingUsuario.setComuna( u.getComuna() );
        existingUsuario.setTelefono( u.getTelefono() );
        existingUsuario.setTipo( u.getTipo() );
        existingUsuario.setContrasenha( u.getContrasenha() );

        return repository.save(existingUsuario);
    }

    public Usuario getUsuarioByNombre(String nombre){
        return repository.findByNombre(nombre);
    }

    public Usuario getUsuarioByCorreo(String correo){
        return repository.findByCorreo(correo);
    }

    public Usuario getUsuarioByTipo(String tipo){
        return repository.findByTipo(tipo);
    }
    
}
