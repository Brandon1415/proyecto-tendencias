/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.ConexionDB;
import model.BajaLibro;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BajaLibroDAO {
    
    /**
     * Obtener todas las bajas (para el historial)
     */
    public List<Map<String, Object>> listarTodas() throws SQLException {
        String sql = "SELECT * FROM vista_historial_bajas ORDER BY fecha_baja DESC";
        List<Map<String, Object>> bajas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> baja = new HashMap<>();
                baja.put("id_baja", rs.getInt("id_baja"));
                baja.put("libro", rs.getString("libro"));
                baja.put("autor", rs.getString("autor"));
                baja.put("isbn", rs.getString("isbn"));
                baja.put("motivo", rs.getString("motivo"));
                baja.put("descripcion", rs.getString("descripcion"));
                baja.put("registrado_por", rs.getString("registrado_por"));
                baja.put("fecha_baja", rs.getDate("fecha_baja"));
                baja.put("fecha_registro", rs.getTimestamp("fecha_registro"));
                bajas.add(baja);
            }
        }
        
        return bajas;
    }
    
    /**
     * Obtener una baja por ID
     */
    public BajaLibro obtenerPorId(int idBaja) throws SQLException {
        String sql = "SELECT b.*, l.titulo, l.autor, l.isbn, " +
                     "CONCAT(u.nombre, ' ', u.apellido) as nombre_usuario " +
                     "FROM bajas_libros b " +
                     "INNER JOIN libros l ON b.id_libro = l.id_libro " +
                     "INNER JOIN usuarios u ON b.id_usuario = u.id_usuario " +
                     "WHERE b.id_baja = ?";
        BajaLibro baja = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBaja);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    baja = mapearBajaCompleta(rs);
                }
            }
        }
        
        return baja;
    }
    
    /**
     * Insertar una nueva baja
     */
    public boolean insertar(BajaLibro baja) throws SQLException {
        String sql = "INSERT INTO bajas_libros (id_libro, id_usuario, motivo, descripcion, fecha_baja) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, baja.getIdLibro());
            ps.setInt(2, baja.getIdUsuario());
            ps.setString(3, baja.getMotivo());
            ps.setString(4, baja.getDescripcion());
            ps.setDate(5, baja.getFechaBaja());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * Actualizar una baja existente
     */
    public boolean actualizar(BajaLibro baja) throws SQLException {
        String sql = "UPDATE bajas_libros SET motivo = ?, descripcion = ?, fecha_baja = ? " +
                     "WHERE id_baja = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, baja.getMotivo());
            ps.setString(2, baja.getDescripcion());
            ps.setDate(3, baja.getFechaBaja());
            ps.setInt(4, baja.getIdBaja());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * Eliminar una baja
     */
    public boolean eliminar(int idBaja) throws SQLException {
        String sql = "DELETE FROM bajas_libros WHERE id_baja = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBaja);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * Contar total de bajas
     */
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM bajas_libros";
        int total = 0;
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
        }
        
        return total;
    }
    
    /**
     * Obtener bajas por libro
     */
    public List<BajaLibro> obtenerPorLibro(int idLibro) throws SQLException {
        String sql = "SELECT * FROM bajas_libros WHERE id_libro = ? ORDER BY fecha_baja DESC";
        List<BajaLibro> bajas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLibro);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bajas.add(mapearBaja(rs));
                }
            }
        }
        
        return bajas;
    }
    
    /**
     * Mapear ResultSet a objeto BajaLibro (solo campos de tabla)
     */
    private BajaLibro mapearBaja(ResultSet rs) throws SQLException {
        BajaLibro baja = new BajaLibro();
        baja.setIdBaja(rs.getInt("id_baja"));
        baja.setIdLibro(rs.getInt("id_libro"));
        baja.setIdUsuario(rs.getInt("id_usuario"));
        baja.setMotivo(rs.getString("motivo"));
        baja.setDescripcion(rs.getString("descripcion"));
        baja.setFechaBaja(rs.getDate("fecha_baja"));
        baja.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return baja;
    }
    
    /**
     * Mapear ResultSet a objeto BajaLibro completo (con JOIN)
     */
    private BajaLibro mapearBajaCompleta(ResultSet rs) throws SQLException {
        BajaLibro baja = mapearBaja(rs);
        baja.setTituloLibro(rs.getString("titulo"));
        baja.setAutorLibro(rs.getString("autor"));
        baja.setIsbnLibro(rs.getString("isbn"));
        baja.setNombreUsuario(rs.getString("nombre_usuario"));
        return baja;
    }
}