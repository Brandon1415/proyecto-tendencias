/*
 * Modelo Usuario
 * ✅ VERSIÓN CORREGIDA - Sin campos eliminados de la BD
 */
package model;

public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String rol;
    private String estado;
    
    public Usuario() {
    }
    
    public Usuario(String nombre, String apellido, String email, String password, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estado = "ACTIVO";
    }
    
    // GETTERS Y SETTERS
    
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
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    // MÉTODOS ÚTILES
    
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
                '}';
    }
}