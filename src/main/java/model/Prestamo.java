/*
 * Modelo Prestamo
 * 
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Prestamo {
    
    private int idPrestamo;
    private int idLibro;
    private String tituloLibro;
    private String isbnLibro;
    private int idLector;
    private String nombreLector;
    private String cedulaLector;
    private int idUsuario;
    private String nombreEmpleado;
    private Date fechaPrestamo;
    private Date fechaDevolucionEsperada;
    private Date fechaDevolucionReal;
    private String estado;
    private String observaciones;
    private Timestamp fechaRegistro;
    private Timestamp fechaModificacion;
    
    public Prestamo() {
    }
    
    public Prestamo(int idLibro, int idLector, int idUsuario) {
        this.idLibro = idLibro;
        this.idLector = idLector;
        this.idUsuario = idUsuario;
        this.estado = "ACTIVO";
    }
    
    // GETTERS Y SETTERS
    
    public int getIdPrestamo() {
        return idPrestamo;
    }
    
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
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
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombreEmpleado() {
        return nombreEmpleado;
    }
    
    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
    
    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public Date getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }
    
    public void setFechaDevolucionEsperada(Date fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }
    
    public Date getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }
    
    public void setFechaDevolucionReal(Date fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
    
    // MÉTODOS ÚTILES
    
    public boolean estaActivo() {
        return "ACTIVO".equalsIgnoreCase(this.estado);
    }
    
    public boolean estaAtrasado() {
        return "ATRASADO".equalsIgnoreCase(this.estado);
    }
    
    public boolean estaDevuelto() {
        return "DEVUELTO".equalsIgnoreCase(this.estado);
    }
    
    @Override
    public String toString() {
        return "Prestamo{" +
                "idPrestamo=" + idPrestamo +
                ", tituloLibro='" + tituloLibro + '\'' +
                ", nombreLector='" + nombreLector + '\'' +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucionEsperada=" + fechaDevolucionEsperada +
                ", estado='" + estado + '\'' +
                '}';
    }
}