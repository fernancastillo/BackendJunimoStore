package com.Junimo.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "DETALLE_ORDEN")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "numero_orden", nullable = false)
    @JsonIgnore
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "codigo_producto", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private int cantidad;

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
        return "DetalleOrden [id=" + id + ", orden=" + orden 
                + ", producto=" + producto + ", cantidad=" + cantidad + "]";
    }
}
