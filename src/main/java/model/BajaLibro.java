package model;

import java.sql.Date;
import java.sql.Timestamp;

public class BajaLibro {
    private int idBaja;
    private int idLibro;
    private int idUsuario;
    private String motivo;
    private String descripcion;
    private Date fechaBaja;
    private Timestamp fechaRegistro;
    
    // Campos adicionales para vistas
    private String tituloLibro;
    private String autorLibro;
    private String isbnLibro;
    private String nombreUsuario;
    
    // Constructor vacío
    public BajaLibro() {
    }
    
    // Constructor con parámetros principales
    public BajaLibro(int idBaja, int idLibro, int idUsuario, String motivo, String descripcion) {
        this.idBaja = idBaja;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.motivo = motivo;
        this.descripcion = descripcion;
    }
    
    // Getters y Setters
    public int getIdBaja() {
        return idBaja;
    }
    
    public void setIdBaja(int idBaja) {
        this.idBaja = idBaja;
    }
    
    public int getIdLibro() {
        return idLibro;
    }
    
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Date getFechaBaja() {
        return fechaBaja;
    }
    
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public String getTituloLibro() {
        return tituloLibro;
    }
    
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    
    public String getAutorLibro() {
        return autorLibro;
    }
    
    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }
    
    public String getIsbnLibro() {
        return isbnLibro;
    }
    
    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    @Override
    public String toString() {
        return "BajaLibro{" +
                "idBaja=" + idBaja +
                ", idLibro=" + idLibro +
                ", motivo='" + motivo + '\'' +
                ", fechaBaja=" + fechaBaja +
                '}';
    }
}