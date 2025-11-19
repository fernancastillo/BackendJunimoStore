package com.Junimo.Entity;

import jakarta.persistence.*;

/**
 *
 * @author scarleth
 */

@Entity
@Table(name="PRODUCTO")
public class Producto {

    @Id
    @Column(name = "codigo_producto", length = 20, nullable = false)
    private String codigo;

    @Column(name="nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name="descripcion", length = 300, nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @Column(name="precio", nullable = false)
    private double precio;

    @Column(name="stock_actual", nullable = false)
    private int stockActual;

    @Column(name="stock_critico", nullable = false)
    private int stockCritico;

    @Column(name="imagen", length = 255, nullable = true)
    private String imagen;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockCritico() {
        return stockCritico;
    }

    public void setStockCritico(int stockCritico) {
        this.stockCritico = stockCritico;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Producto [codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", categoria="
                + categoria + ", precio=" + precio + ", stockActual=" + stockActual + ", stockCritico=" + stockCritico
                + ", imagen=" + imagen + "]";
    }

    
}