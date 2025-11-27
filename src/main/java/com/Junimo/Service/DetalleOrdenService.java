package com.Junimo.Service;

import com.Junimo.Entity.DetalleOrden;
import com.Junimo.Entity.Orden;
import com.Junimo.Entity.Producto;
import com.Junimo.Repository.DetalleOrdenRepository;
import com.Junimo.Repository.OrdenRepository;
import com.Junimo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *
 * @author Fernando
 */
@Service
@Transactional
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository repository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public DetalleOrden saveDetalle(DetalleOrden detalle) {
        try {
            // Validar que la orden existe
            if (detalle.getOrden() == null || detalle.getOrden().getNumeroOrden() == null) {
                throw new RuntimeException("Error al crear el detalle de orden: La orden es requerida");
            }
            
            // Buscar y asignar la orden existente
            String numeroOrden = detalle.getOrden().getNumeroOrden();
            Orden ordenExistente = ordenRepository.findById(numeroOrden)
                .orElseThrow(() -> new RuntimeException("Error al crear el detalle de orden: Orden no encontrada con número: " + numeroOrden));
            detalle.setOrden(ordenExistente);

            // Validar que el producto existe
            if (detalle.getProducto() == null || detalle.getProducto().getCodigo() == null) {
                throw new RuntimeException("Error al crear el detalle de orden: El producto es requerido");
            }

            // Buscar y asignar el producto existente
            String codigoProducto = detalle.getProducto().getCodigo();
            Producto productoExistente = productoRepository.findById(codigoProducto)
                .orElseThrow(() -> new RuntimeException("Error al crear el detalle de orden: Producto no encontrado con código: " + codigoProducto));
            detalle.setProducto(productoExistente);

            // Validar cantidad (manteniendo el mismo formato de mensaje de error)
            if (detalle.getCantidad() <= 0) {
                throw new RuntimeException("Error al crear el detalle de orden: La cantidad debe ser mayor a 0");
            }

            // Validar stock disponible
            if (productoExistente.getStockActual() < detalle.getCantidad()) {
                throw new RuntimeException("Error al crear el detalle de orden: Stock insuficiente para el producto " + 
                    productoExistente.getNombre() + ". Stock disponible: " + productoExistente.getStockActual());
            }

            // Actualizar stock del producto
            int nuevoStock = productoExistente.getStockActual() - detalle.getCantidad();
            productoExistente.setStockActual(nuevoStock);
            productoRepository.save(productoExistente);

            return repository.save(detalle);
            
        } catch (RuntimeException e) {
            // Mantener el mismo formato de excepción
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el detalle de orden: " + e.getMessage());
        }
    }

    public List<DetalleOrden> getDetalles() {
        return repository.findAll();
    }

    public DetalleOrden getDetalleById(int id) {
        return repository.findById(id).orElse(null);
    }
}