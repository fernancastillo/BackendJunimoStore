package com.Junimo.Controller;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Junimo.Entity.Categoria;
import com.Junimo.Service.CategoriaService;

/**
 *
 * @author scarleth
 */

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {
    
    @Autowired
    private CategoriaService service;
    
    @PostMapping("/addCategoria")
    public ResponseEntity<?> addCategoria(@RequestBody Categoria categoria){
        try {
            Categoria categoriaGuardada = service.saveCategoria(categoria);
            return ResponseEntity.ok(categoriaGuardada);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al crear la categoría: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/categorias")
    public ResponseEntity<?> findAllCategorias(){
        try {
            List<Categoria> categorias = service.getCategorias();
            if (categorias.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "No hay categorías registradas en el sistema");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al obtener la lista de categorías: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/categoriaById/{id}")
    public ResponseEntity<?> findCategoriaById(@PathVariable int id){
        try {
            Categoria categoria = service.getCategoriaById(id);
            if (categoria == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Categoría con ID " + id + " no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar categoría: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/categoriaByName/{nombre}")
    public ResponseEntity<?> findByNombre(@PathVariable String nombre){
        try {
            Categoria categoria = service.getCategoriaByNombre(nombre);
            if (categoria == null) {
                Map<String, String> response = new HashMap<>();
                response.put("mensaje", "Categoría con nombre '" + nombre + "' no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Error al buscar categoría por nombre: " + e.getMessage());
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