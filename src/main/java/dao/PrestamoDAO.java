/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.ConexionDB;
import model.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrestamoDAO {
    
    public boolean registrarPrestamo(int idLibro, int idLector, int idUsuario) throws SQLException {
        String sql = "{CALL sp_registrar_prestamo(?, ?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idLibro);
            cs.setInt(2, idLector);
            cs.setInt(3, idUsuario);
            
            cs.execute();
            return true;
        }
    }
    
    public boolean registrarDevolucion(int idPrestamo) throws SQLException {
        String sql = "{CALL sp_registrar_devolucion(?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idPrestamo);
            cs.execute();
            return true;
        }
    }
    
    public List<Map<String, Object>> listarHistorial() throws SQLException {
        String sql = "SELECT * FROM vista_historial_prestamos ORDER BY fecha_prestamo DESC";
        List<Map<String, Object>> prestamos = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prestamos.add(mapearPrestamoVista(rs));
            }
        }
        
        return prestamos;
    }
    
    public List<Map<String, Object>> listarActivos() throws SQLException {
        String sql = "SELECT * FROM vista_historial_prestamos WHERE estado = 'ACTIVO' ORDER BY fecha_devolucion_esperada";
        List<Map<String, Object>> prestamos = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prestamos.add(mapearPrestamoVista(rs));
            }
        }
        
        return prestamos;
    }
    
    public List<Map<String, Object>> listarAtrasados() throws SQLException {
        String sql = "SELECT * FROM vista_historial_prestamos WHERE estado = 'ATRASADO' ORDER BY dias_retraso DESC";
        List<Map<String, Object>> prestamos = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                prestamos.add(mapearPrestamoVista(rs));
            }
        }
        
        return prestamos;
    }
    
    public List<Map<String, Object>> obtenerPorLector(int idLector) throws SQLException {
        String sql = "SELECT * FROM vista_historial_prestamos WHERE cedula = (SELECT cedula FROM lectores WHERE id_lector = ?) ORDER BY fecha_prestamo DESC";
        List<Map<String, Object>> prestamos = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLector);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prestamos.add(mapearPrestamoVista(rs));
                }
            }
        }
        
        return prestamos;
    }
    
    public List<Map<String, Object>> obtenerEstadisticasPorLector() throws SQLException {
        String sql = "SELECT * FROM vista_prestamos_por_lector ORDER BY total_prestamos DESC";
        List<Map<String, Object>> estadisticas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> stats = new HashMap<>();
                stats.put("id_lector", rs.getInt("id_lector"));
                stats.put("lector", rs.getString("lector"));
                stats.put("cedula", rs.getString("cedula"));
                stats.put("correo", rs.getString("correo"));
                stats.put("telefono", rs.getString("telefono"));
                stats.put("total_prestamos", rs.getInt("total_prestamos"));
                stats.put("prestamos_activos", rs.getInt("prestamos_activos"));
                stats.put("prestamos_devueltos", rs.getInt("prestamos_devueltos"));
                stats.put("prestamos_atrasados", rs.getInt("prestamos_atrasados"));
                estadisticas.add(stats);
            }
        }
        
        return estadisticas;
    }
    
    public Prestamo obtenerPorId(int idPrestamo) throws SQLException {
        String sql = "SELECT * FROM prestamos WHERE id_prestamo = ?";
        Prestamo prestamo = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPrestamo);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prestamo = mapearPrestamo(rs);
                }
            }
        }
        
        return prestamo;
    }
    
    public int contarActivos() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM prestamos WHERE estado = 'ACTIVO'";
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
    
    public int contarAtrasados() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM prestamos WHERE estado = 'ATRASADO'";
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
    
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM prestamos";
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
     * Cancelar un préstamo
     */
    public boolean cancelarPrestamo(int idPrestamo) throws SQLException {
        String sql = "UPDATE prestamos SET estado = 'CANCELADO' WHERE id_prestamo = ? AND estado = 'ACTIVO'";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Actualizar un préstamo completo
     */
    public boolean actualizar(Prestamo prestamo) throws SQLException {
        String sql = "UPDATE prestamos SET id_libro = ?, id_lector = ?, " +
                     "fecha_prestamo = ?, fecha_devolucion_esperada = ?, observaciones = ? " +
                     "WHERE id_prestamo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getIdLibro());
            ps.setInt(2, prestamo.getIdLector());
            ps.setDate(3, prestamo.getFechaPrestamo());
            ps.setDate(4, prestamo.getFechaDevolucionEsperada());
            ps.setString(5, prestamo.getObservaciones());
            ps.setInt(6, prestamo.getIdPrestamo());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Eliminar un préstamo (solo si no está activo)
     */
    public boolean eliminar(int idPrestamo) throws SQLException {
        String sql = "DELETE FROM prestamos WHERE id_prestamo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    private Prestamo mapearPrestamo(ResultSet rs) throws SQLException {
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
        prestamo.setIdLibro(rs.getInt("id_libro"));
        prestamo.setIdLector(rs.getInt("id_lector"));
        prestamo.setIdUsuario(rs.getInt("id_usuario"));
        prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo"));
        prestamo.setFechaDevolucionEsperada(rs.getDate("fecha_devolucion_esperada"));
        prestamo.setFechaDevolucionReal(rs.getDate("fecha_devolucion_real"));
        prestamo.setEstado(rs.getString("estado"));
        prestamo.setObservaciones(rs.getString("observaciones"));
        prestamo.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        return prestamo;
    }
    
    private Map<String, Object> mapearPrestamoVista(ResultSet rs) throws SQLException {
        Map<String, Object> prestamo = new HashMap<>();
        prestamo.put("id_prestamo", rs.getInt("id_prestamo"));
        prestamo.put("libro", rs.getString("libro"));
        prestamo.put("isbn", rs.getString("isbn"));
        prestamo.put("lector", rs.getString("lector"));
        prestamo.put("cedula", rs.getString("cedula"));
        prestamo.put("empleado", rs.getString("empleado"));
        prestamo.put("fecha_prestamo", rs.getDate("fecha_prestamo"));
        prestamo.put("fecha_devolucion_esperada", rs.getDate("fecha_devolucion_esperada"));
        prestamo.put("fecha_devolucion_real", rs.getDate("fecha_devolucion_real"));
        prestamo.put("estado", rs.getString("estado"));
        prestamo.put("esta_atrasado", rs.getString("esta_atrasado"));
        prestamo.put("dias_retraso", rs.getInt("dias_retraso"));
        return prestamo;
    }
}