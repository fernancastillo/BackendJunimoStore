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

    // MÉTODO CORREGIDO para guardar orden con detalles
    @Transactional
    public Orden saveOrden(Orden ordenRequest) {
        try {
            System.out.println("🔄 Procesando orden: " + ordenRequest.getNumeroOrden());
            
            // 1. Verificar que el usuario existe
            Usuario usuario = usuarioRepository.findById(ordenRequest.getUsuario().getRun())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con RUN: " + ordenRequest.getUsuario().getRun()));
            
            // 2. Crear y guardar la orden principal (sin detalles primero)
            Orden orden = new Orden();
            orden.setNumeroOrden(ordenRequest.getNumeroOrden());
            orden.setFecha(ordenRequest.getFecha());
            orden.setUsuario(usuario);
            orden.setEstadoEnvio(ordenRequest.getEstadoEnvio());
            orden.setTotal(ordenRequest.getTotal());
            
            // Guardar orden principal primero
            Orden ordenGuardada = ordenRepository.save(orden);
            System.out.println("✅ Orden principal guardada: " + ordenGuardada.getNumeroOrden());

            // 3. Guardar los detalles de la orden
            if (ordenRequest.getDetalles() != null && !ordenRequest.getDetalles().isEmpty()) {
                for (DetalleOrden detalleRequest : ordenRequest.getDetalles()) {
                    // Verificar que el producto existe
                    Producto producto = productoRepository.findById(detalleRequest.getProducto().getCodigo())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con código: " + detalleRequest.getProducto().getCodigo()));
                    
                    // Crear detalle
                    DetalleOrden detalle = new DetalleOrden();
                    detalle.setOrden(ordenGuardada);
                    detalle.setProducto(producto);
                    detalle.setCantidad(detalleRequest.getCantidad());
                    
                    // Guardar detalle
                    detalleOrdenService.saveDetalle(detalle);
                    System.out.println("✅ Detalle guardado - Producto: " + producto.getCodigo() + ", Cantidad: " + detalle.getCantidad());
                }
            }
            
            // 4. Retornar la orden completa
            return ordenRepository.findById(ordenGuardada.getNumeroOrden())
                .orElseThrow(() -> new RuntimeException("Error al recuperar orden guardada"));
            
        } catch (Exception e) {
            System.err.println("❌ Error al guardar orden: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la orden: " + e.getMessage());
        }
    }

    // Los demás métodos permanecen igual...
    public List<Orden> getOrdenes() {
        return ordenRepository.findAll();
    }

    public Orden getOrdenByNumero(String numero) {
        return ordenRepository.findById(numero).orElse(null);
    }

    public String deleteOrden(String numero) {
        ordenRepository.deleteById(numero);
        return "Orden " + numero + " eliminada correctamente.";
    }

    public Orden updateOrden(Orden o) {
        Orden existingOrden = ordenRepository.findById(o.getNumeroOrden()).orElse(null);
        if (existingOrden == null) {
            throw new RuntimeException("Orden con número de órden: " + o.getNumeroOrden() + ", no encontrado.");
        }
        existingOrden.setUsuario(o.getUsuario());
        existingOrden.setFecha(o.getFecha());
        existingOrden.setEstadoEnvio(o.getEstadoEnvio());
        existingOrden.setTotal(o.getTotal());
        existingOrden.setDetalles(o.getDetalles());

        return ordenRepository.save(existingOrden);
    }

    public List<Orden> getOrdenByRun(int run){
        return ordenRepository.findByUsuarioRun(run);
    }

    public List<Orden> getOrdenByEstado(String estado){
        return ordenRepository.findByEstadoEnvio(estado);
    }

    public List<Orden> getOrdenByFecha(Date fecha){
        return ordenRepository.findByFecha(fecha);
    }
}