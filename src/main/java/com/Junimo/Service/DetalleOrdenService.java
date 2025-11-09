package com.Junimo.Service;

import com.Junimo.Entity.DetalleOrden;
import com.Junimo.Repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 *
 * @author Fernando
 */
@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository repository;

    public DetalleOrden saveDetalle(DetalleOrden d) {
        return repository.save(d);
    }

    public List<DetalleOrden> getDetalles() {
        return repository.findAll();
    }

    public DetalleOrden getDetalleById(int id){
        return repository.findById(id).orElse(null);
    }

    public String deleteDetalle(int id) {
        repository.deleteById(id);
        return "Detalle de Órden con ID: "+ id + "eliminado con éxito!";
    }

    public DetalleOrden updateDetalleOrden(DetalleOrden d){
        DetalleOrden existingDetalleOrden = repository.findById(d.getId() ).orElse(null);
        if (existingDetalleOrden == null) {
        throw new RuntimeException("Detalle de órden con ID: " + d.getId() + " no encontrado.");
        }
        existingDetalleOrden.setOrden( d.getOrden() );
        existingDetalleOrden.setCodigoProducto( d.getCodigoProducto() );
        existingDetalleOrden.setNombreProducto( d.getNombreProducto() );
        existingDetalleOrden.setPrecio( d.getPrecio() );

        return repository.save(existingDetalleOrden);
    }

    public DetalleOrden getDetalleByNombreDetalle (String nombre){
        return repository.findDetalleByNombreProducto(nombre);
    }


}
