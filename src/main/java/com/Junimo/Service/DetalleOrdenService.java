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
}