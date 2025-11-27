package com.Junimo.Controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Junimo.Entity.Usuario;
import com.Junimo.Service.UsuarioService;

/**
 *
 * @author Fernando
 */
@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @GetMapping("/")
    public ResponseEntity<?> getApiStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "API levantada");
        response.put("estado", "Todo bien");
        return ResponseEntity.ok(response);
    }

    @Autowired
    private UsuarioService service;

    @PostMapping("/addUsuario")
    public ResponseEntity<?> addUsuario(@RequestBody Usuario u) {
        try {
            Usuario usuarioGuardado = service.saveUsuario(u);
            return ResponseEntity.ok(usuarioGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al crear el usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/usuariosById/{run}")
    public ResponseEntity<?> findUsuarioById(@PathVariable int run) {
        try {
            Usuario usuario = service.getUsuarioById(run);
            if (usuario == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Usuario con RUN " + run + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> findAllUsuario() {
        try {
            List<Usuario> usuarios = service.getUsuarios();
            if (usuarios.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay usuarios registrados en el sistema");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener la lista de usuarios: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/deleteUsuario/{run}")
    public ResponseEntity<?> deleteUsuario(@PathVariable int run) {
        try {
            Usuario usuarioExistente = service.getUsuarioById(run);
            if (usuarioExistente == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No se puede eliminar. Usuario con RUN " + run + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            String resultado = service.deleteUsuario(run);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", resultado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al eliminar usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/updateUsuario")
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario u) {
        try {
            Usuario usuarioActualizado = service.updateUsuario(u);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al actualizar usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/usuariosByCorreo/{correo}")
    public ResponseEntity<?> findUsuarioByCorreo(@PathVariable String correo) {
        try {
            Usuario usuario = service.getUsuarioByCorreo(correo);
            if (usuario == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Usuario con correo " + correo + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar usuario por correo: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/usuariosByTipo/{tipo}")
    public ResponseEntity<?> findUsuarioByTipo(@PathVariable String tipo) {
        try {
            Usuario usuario = service.getUsuarioByTipo(tipo);
            if (usuario == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Usuario con tipo " + tipo + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar usuario por tipo: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Error en el servidor: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}