/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.ConexionDB;
import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    public Usuario autenticar(String email, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = MD5(?) AND estado = 'ACTIVO'";
        Usuario usuario = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }
        }
        
        return usuario;
    }
    
    public boolean estaBloqueado(String email) throws SQLException {
        String sql = "SELECT bloqueado, fecha_bloqueo FROM usuarios WHERE email = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    boolean bloqueado = rs.getBoolean("bloqueado");
                    Timestamp fechaBloqueo = rs.getTimestamp("fecha_bloqueo");
                    
                    if (bloqueado && fechaBloqueo != null) {
                        long minutosBloqueado = (System.currentTimeMillis() - fechaBloqueo.getTime()) / 60000;
                        return minutosBloqueado < 5;
                    }
                }
            }
        }
        
        return false;
    }
    
    public void registrarIntentoFallido(String email) throws SQLException {
        String sql = "{CALL sp_registrar_intento_fallido(?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setString(1, email);
            cs.execute();
        }
    }
    
    public void resetearIntentos(String email) throws SQLException {
        String sql = "{CALL sp_resetear_intentos(?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setString(1, email);
            cs.execute();
        }
    }
    
    public boolean desbloquearUsuario(int idUsuario) throws SQLException {
        String sql = "{CALL sp_desbloquear_usuario(?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idUsuario);
            cs.execute();
            return true;
        }
    }
    
    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuarios ORDER BY fecha_creacion DESC";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        
        return usuarios;
    }
    
    public List<Usuario> listarBloqueados() throws SQLException {
        String sql = "SELECT * FROM vista_usuarios_bloqueados";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
                usuario.setIntentosFallidos(rs.getInt("intentos_fallidos"));
                usuario.setFechaBloqueo(rs.getTimestamp("fecha_bloqueo"));
                usuario.setBloqueado(true);
                usuarios.add(usuario);
            }
        }
        
        return usuarios;
    }
    
    public boolean insertar(Usuario usuario) throws SQLException {
        String sql = "{CALL sp_insertar_usuario(?, ?, ?, MD5(?), ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setString(1, usuario.getNombre());
            cs.setString(2, usuario.getApellido());
            cs.setString(3, usuario.getEmail());
            cs.setString(4, usuario.getPassword());
            cs.setString(5, usuario.getRol());
            
            cs.execute();
            return true;
        }
    }
    
    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, usuario.getIdUsuario());
            cs.setString(2, usuario.getNombre());
            cs.setString(3, usuario.getApellido());
            cs.setString(4, usuario.getEmail());
            cs.setString(5, usuario.getRol());
            cs.setString(6, usuario.getEstado());
            
            cs.execute();
            return true;
        }
    }
    
    public Usuario obtenerPorId(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        Usuario usuario = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }
        }
        
        return usuario;
    }
    
    public Usuario obtenerPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        Usuario usuario = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }
        }
        
        return usuario;
    }
    
        public boolean eliminar(int idUsuario) throws SQLException {
    String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
    
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
         ps.setInt(1, idUsuario);
         int filasAfectadas = ps.executeUpdate();
         return filasAfectadas > 0;
        }
    }
    
    
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setEmail(rs.getString("email"));
        usuario.setRol(rs.getString("rol"));
        usuario.setIntentosFallidos(rs.getInt("intentos_fallidos"));
        usuario.setBloqueado(rs.getBoolean("bloqueado"));
        usuario.setFechaBloqueo(rs.getTimestamp("fecha_bloqueo"));
        usuario.setEstado(rs.getString("estado"));
        usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
        usuario.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        return usuario;
    }

}