/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Timestamp;

public class Libro {
    
    private int idLibro;
    private String titulo;
    private String autor;
    private String isbn;
    private int idCategoria;
    private String nombreCategoria;
    private String editorial;
    private int anioPublicacion;
    private int copiasTotales;
    private int copiasDisponibles;
    private boolean activo;
    private Timestamp fechaRegistro;
    private Timestamp fechaModificacion;
    
    public Libro() {
    }
    
    public Libro(String titulo, String autor, String isbn, int idCategoria, String editorial, int anioPublicacion, int copiasTotales) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.idCategoria = idCategoria;
        this.editorial = editorial;
        this.anioPublicacion = anioPublicacion;
        this.copiasTotales = copiasTotales;
        this.copiasDisponibles = copiasTotales;
        this.activo = true;
    }
    
    public int getIdLibro() {
        return idLibro;
    }
    
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public int getIdCategoria() {
        return idCategoria;
    }
    
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public String getNombreCategoria() {
        return nombreCategoria;
    }
    
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    public String getEditorial() {
        return editorial;
    }
    
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public int getCopiasTotales() {
        return copiasTotales;
    }
    
    public void setCopiasTotales(int copiasTotales) {
        this.copiasTotales = copiasTotales;
    }
    
    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }
    
    public void setCopiasDisponibles(int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public int getCopiasPrestadas() {
        return copiasTotales - copiasDisponibles;
    }
    
    public boolean hayCopiasDisponibles() {
        return copiasDisponibles > 0;
    }
    
    public String getEstadoLibro() {
        if (!activo) return "BAJA";
        if (copiasDisponibles == 0) return "PRESTADO";
        return "DISPONIBLE";
    }
    
    @Override
    public String toString() {
        return "Libro{" +
                "idLibro=" + idLibro +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", editorial='" + editorial + '\'' +
                ", copiasTotales=" + copiasTotales +
                ", copiasDisponibles=" + copiasDisponibles +
                ", activo=" + activo +
                '}';
    }
}