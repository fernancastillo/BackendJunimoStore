package com.Junimo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Junimo.Entity.Producto;
import com.Junimo.Service.ProductoService;

/**
 *
 * @author scarleth
 */

@RestController
@RequestMapping("/v1")
public class ProductoController {
    
    @Autowired
    private ProductoService service;
    
    @PostMapping("/addProducto")
    public Producto addProducto(@RequestBody Producto producto){
        return service.saveProducto(producto);
    }
    
    @GetMapping("/productos")
    public List<Producto> findAllProductos(){
        return service.getProductos();
    }
    
    @GetMapping("/productoById/{codigo}")
    public Producto findProductoById(@PathVariable int codigo){
        return service.getProductoById(codigo);
    }
    
    @GetMapping("/productoByName/{nombre}")
    public Producto findByNombre(@PathVariable String nombre){
        return service.getProductoByNombre(nombre);
    }
    
    @GetMapping("/productosByCategoria/{categoriaId}")
    public List<Producto> findProductosByCategoria(@PathVariable int categoriaId){
        return service.getProductosByCategoria(categoriaId);
    }
    
    @GetMapping("/productosByNombre")
    public List<Producto> findProductosByNombreContaining(@RequestParam String nombre){
        return service.getProductosByNombreContaining(nombre);
    }
    
    @GetMapping("/productosStockCritico")
    public List<Producto> findProductosStockCritico(){
        return service.getProductosStockCritico();
    }
    
    @GetMapping("/productosByPrecio")
    public List<Producto> findProductosByPrecioBetween(
            @RequestParam double precioMin, 
            @RequestParam double precioMax){
        return service.getProductosByPrecioBetween(precioMin, precioMax);
    }
    
    @DeleteMapping("/deleteProducto/{codigo}")
    public String deleteProducto(@PathVariable int codigo){
        return service.deleteProducto(codigo);
    }
    
    @PutMapping("/updateProducto")
    public Producto updateProducto(@RequestBody Producto producto){
        return service.updateProducto(producto);
    }
}