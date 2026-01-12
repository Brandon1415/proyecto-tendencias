/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ASUS
 */

import java.sql.Timestamp;

public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol;
    private int intentosFallidos;
    private boolean bloqueado;
    private Timestamp fechaBloqueo;
    private String estado;
    private Timestamp fechaCreacion;
    private Timestamp fechaModificacion;
    
    public Usuario() {
    }
    
    public Usuario(String nombre, String apellido, String email, String password, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estado = "ACTIVO";
        this.intentosFallidos = 0;
        this.bloqueado = false;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public int getIntentosFallidos() {
        return intentosFallidos;
    }
    
    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }
    
    public boolean isBloqueado() {
        return bloqueado;
    }
    
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
    
    public Timestamp getFechaBloqueo() {
        return fechaBloqueo;
    }
    
    public void setFechaBloqueo(Timestamp fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
    
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    public boolean esAdministrador() {
        return "ADMINISTRADOR".equalsIgnoreCase(this.rol);
    }
    
    public boolean esEmpleado() {
        return "EMPLEADO".equalsIgnoreCase(this.rol);
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", estado='" + estado + '\'' +
                ", bloqueado=" + bloqueado +
                '}';
    }
}