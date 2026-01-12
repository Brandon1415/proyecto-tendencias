/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Reserva {
    
    private int idReserva;
    private int idLibro;
    private String tituloLibro;
    private String isbnLibro;
    private int idLector;
    private String nombreLector;
    private String cedulaLector;
    private Date fechaReserva;
    private Date fechaExpiracion;
    private String estado;
    private Timestamp fechaRegistro;
    private Timestamp fechaModificacion;
    
    public Reserva() {
    }
    
    public Reserva(int idLibro, int idLector) {
        this.idLibro = idLibro;
        this.idLector = idLector;
        this.estado = "ACTIVA";
    }
    
    public int getIdReserva() {
        return idReserva;
    }
    
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }
    
    public int getIdLibro() {
        return idLibro;
    }
    
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
    public String getTituloLibro() {
        return tituloLibro;
    }
    
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    
    public String getIsbnLibro() {
        return isbnLibro;
    }
    
    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }
    
    public int getIdLector() {
        return idLector;
    }
    
    public void setIdLector(int idLector) {
        this.idLector = idLector;
    }
    
    public String getNombreLector() {
        return nombreLector;
    }
    
    public void setNombreLector(String nombreLector) {
        this.nombreLector = nombreLector;
    }
    
    public String getCedulaLector() {
        return cedulaLector;
    }
    
    public void setCedulaLector(String cedulaLector) {
        this.cedulaLector = cedulaLector;
    }
    
    public Date getFechaReserva() {
        return fechaReserva;
    }
    
    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    
    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }
    
    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
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
    
    public boolean estaActiva() {
        return "ACTIVA".equalsIgnoreCase(this.estado);
    }
    
    public boolean estaExpirada() {
        return "EXPIRADA".equalsIgnoreCase(this.estado);
    }
    
    public boolean estaCompletada() {
        return "COMPLETADA".equalsIgnoreCase(this.estado);
    }
    
    public boolean estaCancelada() {
        return "CANCELADA".equalsIgnoreCase(this.estado);
    }
    
    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", tituloLibro='" + tituloLibro + '\'' +
                ", nombreLector='" + nombreLector + '\'' +
                ", fechaReserva=" + fechaReserva +
                ", fechaExpiracion=" + fechaExpiracion +
                ", estado='" + estado + '\'' +
                '}';
    }
}