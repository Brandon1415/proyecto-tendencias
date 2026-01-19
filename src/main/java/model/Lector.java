/*
 * Modelo Lector
 * 
 */
package model;

import java.sql.Timestamp;

public class Lector {
    
    private int idLector;
    private String nombre;
    private String apellido;
    private String cedula;
    private String correo;
    private String telefono;
    private String direccion;
    private String estado;
    private Timestamp fechaRegistro;
    
    public Lector() {
    }
    
    public Lector(String nombre, String apellido, String cedula, String correo, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = "ACTIVO";
    }
    
    // GETTERS Y SETTERS
    
    public int getIdLector() {
        return idLector;
    }
    
    public void setIdLector(int idLector) {
        this.idLector = idLector;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    
    // MÉTODOS ÚTILES
    
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    public boolean estaActivo() {
        return "ACTIVO".equalsIgnoreCase(this.estado);
    }
    
    @Override
    public String toString() {
        return "Lector{" +
                "idLector=" + idLector +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", correo='" + correo + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}