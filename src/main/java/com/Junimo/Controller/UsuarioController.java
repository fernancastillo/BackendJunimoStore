package com.Junimo.Controller;

import com.Junimo.Entity.Usuario;
import com.Junimo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {
    
    @Autowired
    private UsuarioService service;
    
    // API Keys hardcodeadas (¡cámbialas en producción!)
    private boolean validarApiKey(String apiKey, String... rolesPermitidos) {
        // Mapa de API Keys -> Tipo de usuario
        Map<String, String> keys = new HashMap<>();
        keys.put("admin", "Administrador");
        keys.put("vendedor", "Vendedor"); 
        keys.put("cliente", "Cliente");
        
        // Verificar si la key existe
        String rol = keys.get(apiKey);
        if (rol == null) {
            return false;
        }
        
        // Si no se especifican roles, cualquier key válida sirve
        if (rolesPermitidos.length == 0) {
            return true;
        }
        
        // Verificar si el rol está permitido
        for (String rolPermitido : rolesPermitidos) {
            if (rolPermitido.equals(rol)) {
                return true;
            }
        }
        
        return false;
    }
    
    // ========== MÉTODOS PÚBLICOS (SIN API KEY) ==========
    
    @GetMapping("/")
    public ResponseEntity<?> getApiStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "API funcionando");
        response.put("estado", "OK");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/addUsuario")
    public ResponseEntity<?> addUsuario(@RequestBody Usuario u) {
        try {
            Usuario usuarioGuardado = service.saveUsuario(u);
            return ResponseEntity.ok(usuarioGuardado);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al crear usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // ========== MÉTODOS PROTEGIDOS (CON API KEY) ==========
    
    @GetMapping("/usuarios")
    public ResponseEntity<?> findAllUsuario(
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            List<Usuario> usuarios = service.getUsuarios();
            if (usuarios.isEmpty()) {
                return ResponseEntity.ok(Map.of("mensaje", "No hay usuarios registrados"));
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/usuariosById/{run}")
    public ResponseEntity<?> findUsuarioById(
            @PathVariable int run,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        if (!validarApiKey(apiKey, "Administrador", "Vendedor", "Cliente")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key válida requerida (Admin, Vendedor o Cliente)"));
        }
        
        try {
            Usuario usuario = service.getUsuarioById(run);
            if (usuario == null) {
                return ResponseEntity.status(404)
                    .body(Map.of("error", "Usuario no encontrado"));
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/usuariosByCorreo/{correo}")
    public ResponseEntity<?> findUsuarioByCorreo(
            @PathVariable String correo,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Usuario usuario = service.getUsuarioByCorreo(correo);
            if (usuario == null) {
                return ResponseEntity.status(404)
                    .body(Map.of("error", "Usuario no encontrado"));
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/usuariosByTipo/{tipo}")
    public ResponseEntity<?> findUsuarioByTipo(
            @PathVariable String tipo,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Usuario usuario = service.getUsuarioByTipo(tipo);
            if (usuario == null) {
                return ResponseEntity.status(404)
                    .body(Map.of("error", "Usuario no encontrado"));
            }
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/updateUsuario")
    public ResponseEntity<?> updateUsuario(
            @RequestBody Usuario u,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        if (!validarApiKey(apiKey, "Administrador", "Cliente", "Vendedor")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador, Vendedor o Cliente requerida"));
        }
        
        try {
            Usuario usuarioActualizado = service.updateUsuario(u);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
    
    @DeleteMapping("/deleteUsuario/{run}")
    public ResponseEntity<?> deleteUsuario(
            @PathVariable int run,
            @RequestHeader(value = "X-API-KEY", required = false) String apiKey) {
        
        if (!validarApiKey(apiKey, "Administrador")) {
            return ResponseEntity.status(401)
                .body(Map.of("error", "API Key de Administrador requerida"));
        }
        
        try {
            Usuario usuarioExistente = service.getUsuarioById(run);
            if (usuarioExistente == null) {
                return ResponseEntity.status(404)
                    .body(Map.of("error", "Usuario no existe"));
            }
            
            String resultado = service.deleteUsuario(run);
            return ResponseEntity.ok(Map.of("mensaje", resultado));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
}