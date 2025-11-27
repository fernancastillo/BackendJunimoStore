package com.Junimo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name = "ORDEN")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Orden {

    @Id
    @Column(name = "numero_orden", length = 20, nullable = false)
    private String numeroOrden;

    @Column(nullable = false)
    private Date fecha;

    @ManyToOne(fetch = FetchType.EAGER) // Cambiado a EAGER para evitar problemas
    @JoinColumn(name = "run", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @Column(name = "estado_envio", nullable = false, length = 50)
    private String estadoEnvio;

    @Column(nullable = false)
    private int total;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"orden", "hibernateLazyInitializer", "handler"})
    private List<DetalleOrden> detalles;

    // ... getters y setters

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEstadoEnvio() {
        return estadoEnvio;
    }

    public void setEstadoEnvio(String estadoEnvio) {
        this.estadoEnvio = estadoEnvio;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DetalleOrden> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrden> detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return "Orden [numeroOrden=" + numeroOrden + ", fecha=" + fecha + ", usuario=" + 
               (usuario != null ? usuario.getRun() : "null") + ", estadoEnvio=" + estadoEnvio + 
               ", total=" + total + ", detalles=" + (detalles != null ? detalles.size() : 0) + " items]";
    }
}