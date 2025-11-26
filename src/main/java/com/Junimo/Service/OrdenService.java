package com.Junimo.Service;

import com.Junimo.Entity.Orden;
import com.Junimo.Entity.DetalleOrden;
import com.Junimo.Entity.Usuario;
import com.Junimo.Entity.Producto;
import com.Junimo.Repository.OrdenRepository;
import com.Junimo.Repository.UsuarioRepository;
import com.Junimo.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.List;

@Service
public class OrdenService {

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DetalleOrdenService detalleOrdenService;

    @Transactional
    public Orden saveOrden(Orden ordenRequest) {
        try {
            Usuario usuario = usuarioRepository.findById(ordenRequest.getUsuario().getRun())
                    .orElseThrow(() -> new RuntimeException(
                            "Usuario no encontrado con RUN: " + ordenRequest.getUsuario().getRun()));
            Orden orden = new Orden();
            orden.setNumeroOrden(ordenRequest.getNumeroOrden());
            orden.setFecha(ordenRequest.getFecha());
            orden.setUsuario(usuario);
            orden.setEstadoEnvio(ordenRequest.getEstadoEnvio());
            orden.setTotal(ordenRequest.getTotal());
            Orden ordenGuardada = ordenRepository.save(orden);
            
            if (ordenRequest.getDetalles() != null && !ordenRequest.getDetalles().isEmpty()) {
                for (DetalleOrden detalleRequest : ordenRequest.getDetalles()) {
                    Producto producto = productoRepository.findById(detalleRequest.getProducto().getCodigo())
                            .orElseThrow(() -> new RuntimeException(
                                    "Producto no encontrado con código: " + detalleRequest.getProducto().getCodigo()));
                    if (producto.getStockActual() < detalleRequest.getCantidad()) {
                        throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() +
                                ". Stock disponible: " + producto.getStockActual() +
                                ", solicitado: " + detalleRequest.getCantidad());
                    }
                    int nuevoStock = producto.getStockActual() - detalleRequest.getCantidad();
                    producto.setStockActual(nuevoStock);
                    productoRepository.save(producto);
                    DetalleOrden detalle = new DetalleOrden();
                    detalle.setOrden(ordenGuardada);
                    detalle.setProducto(producto);
                    detalle.setCantidad(detalleRequest.getCantidad());
                    detalleOrdenService.saveDetalle(detalle);
                }
            }
            return ordenRepository.findById(ordenGuardada.getNumeroOrden())
                    .orElseThrow(() -> new RuntimeException("Error al recuperar orden guardada"));
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la orden: " + e.getMessage());
        }
    }

    public List<Orden> getOrdenes() {
        return ordenRepository.findAll();
    }

    public Orden getOrdenByNumero(String numero) {
        return ordenRepository.findById(numero).orElse(null);
    }

    public String deleteOrden(String numero) {
        if (!ordenRepository.existsById(numero)) {
            throw new RuntimeException("No se puede eliminar. Orden con número " + numero + " no existe");
        }
        ordenRepository.deleteById(numero);
        return "Orden " + numero + " eliminada correctamente.";
    }

    @Transactional
    public Orden updateOrden(Orden o) {
        try {
            Orden existingOrden = ordenRepository.findById(o.getNumeroOrden())
                    .orElseThrow(() -> new RuntimeException("Orden con número: " + o.getNumeroOrden() + " no encontrada."));
            if (o.getEstadoEnvio() != null) {
                existingOrden.setEstadoEnvio(o.getEstadoEnvio());
            }     
            return ordenRepository.save(existingOrden);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la orden: " + e.getMessage());
        }
    }

    @Transactional
    public Orden updateOrdenEstado(String numeroOrden, String nuevoEstado) {
        try {
            Orden existingOrden = ordenRepository.findById(numeroOrden)
                    .orElseThrow(() -> new RuntimeException("Orden con número: " + numeroOrden + " no encontrada."));
            
            existingOrden.setEstadoEnvio(nuevoEstado);
            return ordenRepository.save(existingOrden);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el estado de la orden: " + e.getMessage());
        }
    }

    public List<Orden> getOrdenByRun(int run) {
        return ordenRepository.findByUsuarioRun(run);
    }

    public List<Orden> getOrdenByEstado(String estado) {
        return ordenRepository.findByEstadoEnvio(estado);
    }

    public List<Orden> getOrdenByFecha(Date fecha) {
        return ordenRepository.findByFecha(fecha);
    }
}