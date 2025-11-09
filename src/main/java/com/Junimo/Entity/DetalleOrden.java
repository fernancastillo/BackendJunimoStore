package com.Junimo.Entity;


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
    private Orden orden;

    @Column(name = "codigo_producto", length = 20, nullable = false)
    private String codigoProducto;

    @Column(name = "nombre_producto", length = 100, nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private int precio;

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

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "DetalleOrden [id=" + id + ", orden=" + orden + ", codigoProducto=" + codigoProducto
                + ", nombreProducto=" + nombreProducto + ", cantidad=" + cantidad + ", precio=" + precio + "]";
    }

    
}
