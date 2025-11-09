package com.Junimo.Service;

import com.Junimo.Entity.Orden;
import com.Junimo.Repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Fernando
 */
@Service
public class OrdenService {

    @Autowired
    private OrdenRepository repository;

    public Orden saveOrden(Orden o) {
        return repository.save(o);
    }

    public List<Orden> getOrdenes() {
        return repository.findAll();
    }

    public Orden getOrdenByNumero(String numero) {
        return repository.findById(numero).orElse(null);
    }

    public String deleteOrden(String numero) {
        repository.deleteById(numero);
        return "Orden " + numero + " eliminada correctamente.";
    }

    public Orden updateOrden(Orden o) {
        Orden existingOrden = repository.findById(o.getNumeroOrden()).orElse(null);
        if (existingOrden == null) {
        throw new RuntimeException("Orden con número de órden: " + o.getNumeroOrden() + ", no encontrado.");
        }
        existingOrden.setUsuario(o.getUsuario());
        existingOrden.setFecha(o.getFecha());
        existingOrden.setEstadoEnvio(o.getEstadoEnvio());
        existingOrden.setTotal(o.getTotal());
        existingOrden.setDetalles(o.getDetalles());

        return repository.save(existingOrden);
    }

    public Orden getOrdenByRun(int run){
        return repository.findByRun(run);
    }

    public Orden getOrdenByEstado(String estado){
        return repository.findByEstado(estado);
    }

    public Orden getOrdenByFecha(Date fecha){
        return repository.findByFecha(fecha);
    }

}
