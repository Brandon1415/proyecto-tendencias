/*
 * DAO para gestionar operaciones de Usuarios
 * 
 */
package dao;

import config.ConexionDB;
import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    public Usuario autenticar(String email, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = SHA2(?, 256) AND estado = 'ACTIVO'";
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
    
    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuarios ORDER BY nombre ASC";
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
        String sql = "SELECT * FROM usuarios WHERE estado = 'BLOQUEADO' ORDER BY id_usuario DESC";
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
    
    public boolean insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, email, password, rol, estado) " +
                     "VALUES (?, ?, ?, SHA2(?, 256), ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getRol());
            ps.setString(6, usuario.getEstado() != null ? usuario.getEstado() : "ACTIVO");
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * ✅ ACTUALIZAR USUARIO - EDITA LITERALMENTE TODO
     * Permite editar: nombre, apellido, email, rol, estado
     */
    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, rol = ?, estado = ? " +
                     "WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getRol());
            ps.setString(5, usuario.getEstado());
            ps.setInt(6, usuario.getIdUsuario());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
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
    
    public boolean desbloquearUsuario(int idUsuario) throws SQLException {
        String sql = "UPDATE usuarios SET estado = 'ACTIVO' WHERE id_usuario = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
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
        usuario.setEstado(rs.getString("estado"));
        
        return usuario;
    }
}