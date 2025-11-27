package com.Junimo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "DETALLE_ORDEN")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_orden", nullable = false)
    @JsonIgnoreProperties({"detalles", "usuario", "hibernateLazyInitializer", "handler"})
    private Orden orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_producto", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    @Column(nullable = false)
    private int cantidad;

    // ... getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "DetalleOrden [id=" + id + ", orden=" + (orden != null ? orden.getNumeroOrden() : "null") 
                + ", producto=" + (producto != null ? producto.getCodigo() : "null") 
                + ", cantidad=" + cantidad + "]";
    }
}