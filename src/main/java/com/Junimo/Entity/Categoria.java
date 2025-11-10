package com.Junimo.Entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList; 

/**
 *
 * @author scarleth 
 */




@Entity
@Table(name="CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nombre", length = 100, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_padre", nullable = true)
    private Categoria categoriaPadre;

    @OneToMany(mappedBy = "categoriaPadre")
    private List<Categoria> subcategorias;

    public Categoria() {
        this.subcategorias = new ArrayList<>();
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
        this.subcategorias = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoriaPadre() {
        return categoriaPadre;
    }

    public void setCategoriaPadre(Categoria categoriaPadre) {
        this.categoriaPadre = categoriaPadre;
    }

    public List<Categoria> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<Categoria> subcategorias) {
        this.subcategorias = subcategorias;
    }


    public void agregarSubcategoria(Categoria subcategoria) {
        this.subcategorias.add(subcategoria);
        subcategoria.setCategoriaPadre(this);
    }
    
    public void eliminarSubcategoria(Categoria subcategoria) {
        this.subcategorias.remove(subcategoria);
        subcategoria.setCategoriaPadre(null);
    }
}