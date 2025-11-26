package com.Junimo.Service;

import com.Junimo.Entity.Producto;
import com.Junimo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author scarleth
 */

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository repository;
    
    public Producto saveProducto(Producto producto) {
        return repository.save(producto);
    }
    
    public List<Producto> getProductos() {
        return repository.findAll();
    }
    
    public Producto getProductoById(String codigo) {
        return repository.findById(codigo).orElse(null);
    }
    
    public Producto getProductoByNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
    
    public List<Producto> getProductosByCategoria(int categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }
    
    public List<Producto> getProductosStockCritico() {
        return repository.findByStockActualLessThanEqual(10);
    }
    
    public List<Producto> getProductosByPrecioBetween(double precioMin, double precioMax) {
        return repository.findByPrecioBetween(precioMin, precioMax);
    }
    
    public String deleteProducto(String codigo) {
        if (repository.existsById(codigo)) {
            repository.deleteById(codigo);
            return "Producto eliminado: " + codigo;
        } else {
            return "Producto no encontrado: " + codigo;
        }
    }
    
    public Producto updateProducto(Producto producto) {
        Producto existingProducto = repository.findById(producto.getCodigo()).orElse(null);
        if (existingProducto != null) {
            existingProducto.setNombre(producto.getNombre());
            existingProducto.setDescripcion(producto.getDescripcion());
            existingProducto.setCategoria(producto.getCategoria());
            existingProducto.setPrecio(producto.getPrecio());
            existingProducto.setStockActual(producto.getStockActual());
            existingProducto.setStockCritico(producto.getStockCritico());
            existingProducto.setImagen(producto.getImagen());
            return repository.save(existingProducto);
        }
        return null;
    }
}