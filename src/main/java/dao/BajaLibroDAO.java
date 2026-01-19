/*
 * DAO para gestionar el Historial de Bajas de Libros
 * 
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
    
    public List<Map<String, Object>> listarTodas() throws SQLException {
        String sql = "SELECT h.id_baja, h.id_libro, h.id_usuario, " +
                     "l.titulo as libro, l.autor, l.isbn, " +
                     "h.motivo, h.descripcion, " +
                     "CONCAT(u.nombre, ' ', u.apellido) as registrado_por, " +
                     "h.fecha_baja, h.fecha_registro, h.fecha_modificacion " +
                     "FROM historial_bajas h " +
                     "INNER JOIN libros l ON h.id_libro = l.id_libro " +
                     "INNER JOIN usuarios u ON h.id_usuario = u.id_usuario " +
                     "ORDER BY h.fecha_baja DESC";
        
        List<Map<String, Object>> bajas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> baja = new HashMap<>();
                baja.put("id_baja", rs.getInt("id_baja"));
                baja.put("id_libro", rs.getInt("id_libro"));
                baja.put("id_usuario", rs.getInt("id_usuario"));
                baja.put("libro", rs.getString("libro"));
                baja.put("autor", rs.getString("autor"));
                baja.put("isbn", rs.getString("isbn"));
                baja.put("motivo", rs.getString("motivo"));
                baja.put("descripcion", rs.getString("descripcion"));
                baja.put("registrado_por", rs.getString("registrado_por"));
                baja.put("fecha_baja", rs.getDate("fecha_baja"));
                baja.put("fecha_registro", rs.getTimestamp("fecha_registro"));
                baja.put("fecha_modificacion", rs.getTimestamp("fecha_modificacion"));
                bajas.add(baja);
            }
        }
        
        return bajas;
    }
    
    public BajaLibro obtenerPorId(int idBaja) throws SQLException {
        String sql = "SELECT h.*, l.titulo, l.autor, l.isbn, " +
                     "CONCAT(u.nombre, ' ', u.apellido) as nombre_usuario " +
                     "FROM historial_bajas h " +
                     "INNER JOIN libros l ON h.id_libro = l.id_libro " +
                     "INNER JOIN usuarios u ON h.id_usuario = u.id_usuario " +
                     "WHERE h.id_baja = ?";
        
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
    
    public boolean insertar(BajaLibro baja) throws SQLException {
        String sql = "INSERT INTO historial_bajas (id_libro, id_usuario, motivo, descripcion, fecha_baja) " +
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
     * ✅ ACTUALIZAR BAJA - EDITA LITERALMENTE TODO
     * Incluyendo fecha_baja, fecha_registro y fecha_modificacion
     */
    public boolean actualizar(BajaLibro baja) throws SQLException {
        String sql = "UPDATE historial_bajas SET " +
                     "id_libro = ?, " +
                     "id_usuario = ?, " +
                     "motivo = ?, " +
                     "descripcion = ?, " +
                     "fecha_baja = ?, " +
                     "fecha_registro = ?, " +
                     "fecha_modificacion = ? " +
                     "WHERE id_baja = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, baja.getIdLibro());
            ps.setInt(2, baja.getIdUsuario());
            ps.setString(3, baja.getMotivo());
            ps.setString(4, baja.getDescripcion());
            ps.setDate(5, baja.getFechaBaja());
            
            if (baja.getFechaRegistro() != null) {
                ps.setTimestamp(6, baja.getFechaRegistro());
            } else {
                ps.setNull(6, java.sql.Types.TIMESTAMP);
            }
            
            if (baja.getFechaModificacion() != null) {
                ps.setTimestamp(7, baja.getFechaModificacion());
            } else {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            }
            
            ps.setInt(8, baja.getIdBaja());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    public boolean eliminar(int idBaja) throws SQLException {
        String sql = "DELETE FROM historial_bajas WHERE id_baja = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idBaja);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM historial_bajas";
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
    
    public List<BajaLibro> obtenerPorLibro(int idLibro) throws SQLException {
        String sql = "SELECT * FROM historial_bajas WHERE id_libro = ? ORDER BY fecha_baja DESC";
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
    
    private BajaLibro mapearBaja(ResultSet rs) throws SQLException {
        BajaLibro baja = new BajaLibro();
        
        baja.setIdBaja(rs.getInt("id_baja"));
        baja.setIdLibro(rs.getInt("id_libro"));
        baja.setIdUsuario(rs.getInt("id_usuario"));
        baja.setMotivo(rs.getString("motivo"));
        baja.setDescripcion(rs.getString("descripcion"));
        baja.setFechaBaja(rs.getDate("fecha_baja"));
        baja.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        baja.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        
        return baja;
    }
    
    private BajaLibro mapearBajaCompleta(ResultSet rs) throws SQLException {
        BajaLibro baja = mapearBaja(rs);
        
        baja.setTituloLibro(rs.getString("titulo"));
        baja.setAutorLibro(rs.getString("autor"));
        baja.setIsbnLibro(rs.getString("isbn"));
        baja.setNombreUsuario(rs.getString("nombre_usuario"));
        
        return baja;
    }
}