package com.Junimo.Entity;

import java.sql.Date;
import jakarta.persistence.*;

/**
 *
 * @author Fernando
 */
@Entity
@Table(name="USUARIO")
public class Usuario {
    
    @Id
    private int run;
    
    @Column(name="nombre", length=40, nullable = false)
    private String nombre;
    
    @Column(name="apellidos", length=70, nullable = false)
    private String apellidos;
    
    @Column(name="correo", length=100, nullable = false)
    private String correo;

    @Column(name="direccion", length=200, nullable = false)
    private String direccion;

    @Column(name="fecha_nac", nullable = false)
    private Date fechaNac;

    @Column(name="region", nullable = false)
    private String region;

    @Column(name="comuna", nullable = false)
    private String comuna;

    @Column(name="telefono", length=9, nullable = true)
    private int telefono;

    @Column(name="tipo", nullable = false)
    private String tipo;

    @Column(name="contrasenha", length=10, nullable = false)
    private String contrasenha;


    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fecha_nac) {
        this.fechaNac = fecha_nac;
    }
    
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    @Override
    public String toString() {
        return "Usuario [run=" + run + ", nombre=" + nombre + ", apellidos=" + apellidos + ", correo=" + correo
                + ", direccion=" + direccion + ", fechaNac=" + fechaNac + ", region=" + region + ", comuna=" + comuna
                + ", telefono=" + telefono + ", tipo=" + tipo + ", contrasenha=" + contrasenha + "]";
    }

    
}