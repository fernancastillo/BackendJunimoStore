package com.Junimo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.Junimo.Entity.Usuario;
import com.Junimo.Service.UsuarioService;

/**
 *
 * @author Fernando
 */
@RestController

public class UsuarioController {
    
    @Autowired
    private UsuarioService service;
    
    @PostMapping("/addUsuario")
    public Usuario addUsuario(@RequestBody Usuario u){
        return service.saveUsuario(u);
    }

    @GetMapping("/usuariosById/{run}")
    public Usuario findUsuarioById(@PathVariable int run){
        return service.getUsuarioById(run);
    }
    
    @GetMapping("/usuarios")
    public List<Usuario> findAllUsuario(){
        return service.getUsuarios();
    }
    
    @DeleteMapping("/delete/{run}")
    public String deleteEmployee(@PathVariable int run){
        return service.deleteUsuario(run);
    }
    
    @PutMapping("/update")
    public Usuario updateUsuario(@RequestBody Usuario u){
        return service.updateUsuario(u);
    }

    @GetMapping("/usuariosByCorreo/{correo}")
    public Usuario findUsuarioByCorreo(@PathVariable String correo){
        return service.getUsuarioByCorreo(correo);
    }

    @GetMapping("/usuariosByTipo/{tipo}")
    public Usuario findUsuarioByTipo(@PathVariable String tipo){
        return service.getUsuarioByTipo(tipo);
    }
    
}   