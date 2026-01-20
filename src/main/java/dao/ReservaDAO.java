/*
 * DAO para gestionar operaciones de Reservas
 * 
 */
package dao;

import config.ConexionDB;
import model.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservaDAO {
    
    public boolean crearReserva(int idLibro, int idLector) throws SQLException {
        String sql = "{CALL sp_crear_reserva(?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idLibro);
            cs.setInt(2, idLector);
            cs.execute();
            return true;
        }
    }
    
    public boolean cancelarReserva(int idReserva) throws SQLException {
        String sql = "{CALL sp_cancelar_reserva(?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idReserva);
            cs.execute();
            return true;
        }
    }
    
    public boolean completarReserva(int idReserva) throws SQLException {
        String sql = "{CALL sp_completar_reserva(?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idReserva);
            cs.execute();
            return true;
        }
    }
    
    /**
     * Listar TODAS las reservas con TODAS las columnas
     */
    public List<Map<String, Object>> listarTodas() throws SQLException {
        String sql = "SELECT " +
                     "r.id_reserva, " +
                     "r.id_libro, " +
                     "r.id_lector, " +
                     "r.estado, " +
                     "r.fecha_reserva, " +
                     "r.fecha_expiracion, " +
                     "r.fecha_registro, " +
                     "r.fecha_modificacion, " +
                     "l.titulo as libro, " +
                     "l.isbn, " +
                     "CONCAT(lec.nombre, ' ', lec.apellido) as lector, " +
                     "lec.cedula, " +
                     "lec.telefono, " +
                     "DATEDIFF(r.fecha_expiracion, CURDATE()) as dias_restantes " +
                     "FROM reservas r " +
                     "INNER JOIN libros l ON r.id_libro = l.id_libro " +
                     "INNER JOIN lectores lec ON r.id_lector = lec.id_lector " +
                     "ORDER BY r.fecha_reserva DESC";
        
        List<Map<String, Object>> reservas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> reserva = new HashMap<>();
                
                reserva.put("id_reserva", rs.getInt("id_reserva"));
                reserva.put("id_libro", rs.getInt("id_libro"));
                reserva.put("id_lector", rs.getInt("id_lector"));
                reserva.put("estado", rs.getString("estado"));
                reserva.put("fecha_reserva", rs.getDate("fecha_reserva"));
                reserva.put("fecha_expiracion", rs.getDate("fecha_expiracion"));
                reserva.put("fecha_registro", rs.getTimestamp("fecha_registro"));
                reserva.put("fecha_modificacion", rs.getTimestamp("fecha_modificacion"));
                reserva.put("libro", rs.getString("libro"));
                reserva.put("isbn", rs.getString("isbn"));
                reserva.put("lector", rs.getString("lector"));
                reserva.put("cedula", rs.getString("cedula"));
                reserva.put("telefono", rs.getString("telefono"));
                reserva.put("dias_restantes", rs.getInt("dias_restantes"));
                
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            System.err.println("ERROR en listarTodas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
        return reservas;
    }
    
    public List<Map<String, Object>> listarActivas() throws SQLException {
        String sql = "SELECT " +
                     "r.id_reserva, " +
                     "r.id_libro, " +
                     "r.id_lector, " +
                     "r.estado, " +
                     "r.fecha_reserva, " +
                     "r.fecha_expiracion, " +
                     "r.fecha_registro, " +
                     "r.fecha_modificacion, " +
                     "l.titulo as libro, " +
                     "l.isbn, " +
                     "CONCAT(lec.nombre, ' ', lec.apellido) as lector, " +
                     "lec.cedula, " +
                     "lec.telefono, " +
                     "DATEDIFF(r.fecha_expiracion, CURDATE()) as dias_restantes " +
                     "FROM reservas r " +
                     "INNER JOIN libros l ON r.id_libro = l.id_libro " +
                     "INNER JOIN lectores lec ON r.id_lector = lec.id_lector " +
                     "WHERE r.estado = 'PENDIENTE' " +
                     "ORDER BY r.fecha_expiracion ASC";
        
        List<Map<String, Object>> reservas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> reserva = new HashMap<>();
                reserva.put("id_reserva", rs.getInt("id_reserva"));
                reserva.put("id_libro", rs.getInt("id_libro"));
                reserva.put("id_lector", rs.getInt("id_lector"));
                reserva.put("estado", rs.getString("estado"));
                reserva.put("fecha_reserva", rs.getDate("fecha_reserva"));
                reserva.put("fecha_expiracion", rs.getDate("fecha_expiracion"));
                reserva.put("fecha_registro", rs.getTimestamp("fecha_registro"));
                reserva.put("fecha_modificacion", rs.getTimestamp("fecha_modificacion"));
                reserva.put("libro", rs.getString("libro"));
                reserva.put("isbn", rs.getString("isbn"));
                reserva.put("lector", rs.getString("lector"));
                reserva.put("cedula", rs.getString("cedula"));
                reserva.put("telefono", rs.getString("telefono"));
                reserva.put("dias_restantes", rs.getInt("dias_restantes"));
                
                reservas.add(reserva);
            }
        }
        
        return reservas;
    }
    
    public List<Map<String, Object>> listarPorLector(int idLector) throws SQLException {
        String sql = "SELECT " +
                     "r.id_reserva, " +
                     "r.id_libro, " +
                     "r.id_lector, " +
                     "r.estado, " +
                     "r.fecha_reserva, " +
                     "r.fecha_expiracion, " +
                     "r.fecha_registro, " +
                     "r.fecha_modificacion, " +
                     "l.titulo as libro, " +
                     "l.isbn, " +
                     "CONCAT(lec.nombre, ' ', lec.apellido) as lector, " +
                     "lec.cedula, " +
                     "lec.telefono, " +
                     "DATEDIFF(r.fecha_expiracion, CURDATE()) as dias_restantes " +
                     "FROM reservas r " +
                     "INNER JOIN libros l ON r.id_libro = l.id_libro " +
                     "INNER JOIN lectores lec ON r.id_lector = lec.id_lector " +
                     "WHERE r.id_lector = ? AND r.estado = 'PENDIENTE'";
        
        List<Map<String, Object>> reservas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLector);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> reserva = new HashMap<>();
                    reserva.put("id_reserva", rs.getInt("id_reserva"));
                    reserva.put("id_libro", rs.getInt("id_libro"));
                    reserva.put("id_lector", rs.getInt("id_lector"));
                    reserva.put("estado", rs.getString("estado"));
                    reserva.put("fecha_reserva", rs.getDate("fecha_reserva"));
                    reserva.put("fecha_expiracion", rs.getDate("fecha_expiracion"));
                    reserva.put("fecha_registro", rs.getTimestamp("fecha_registro"));
                    reserva.put("fecha_modificacion", rs.getTimestamp("fecha_modificacion"));
                    reserva.put("libro", rs.getString("libro"));
                    reserva.put("isbn", rs.getString("isbn"));
                    reserva.put("lector", rs.getString("lector"));
                    reserva.put("cedula", rs.getString("cedula"));
                    reserva.put("telefono", rs.getString("telefono"));
                    reserva.put("dias_restantes", rs.getInt("dias_restantes"));
                    
                    reservas.add(reserva);
                }
            }
        }
        
        return reservas;
    }
    
    public int contarActivasPorLector(int idLector) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM reservas " +
                     "WHERE id_lector = ? AND estado = 'PENDIENTE'";
        int total = 0;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLector);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }
        }
        
        return total;
    }
    
    public Reserva obtenerPorId(int idReserva) throws SQLException {
        String sql = "SELECT * FROM reservas WHERE id_reserva = ?";
        Reserva reserva = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idReserva);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reserva = new Reserva();
                    reserva.setIdReserva(rs.getInt("id_reserva"));
                    reserva.setIdLibro(rs.getInt("id_libro"));
                    reserva.setIdLector(rs.getInt("id_lector"));
                    reserva.setFechaReserva(rs.getDate("fecha_reserva"));
                    reserva.setFechaExpiracion(rs.getDate("fecha_expiracion"));
                    reserva.setEstado(rs.getString("estado"));
                    reserva.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    reserva.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
                }
            }
        }
        
        return reserva;
    }
    
    /**
     * ✅ ACTUALIZAR RESERVA - Edita TODOS los campos de la tabla
     * Incluye: id_libro, id_lector, fecha_reserva, fecha_expiracion, estado,
     *          fecha_registro, fecha_modificacion
     */
    public boolean actualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reservas SET " +
                     "id_libro = ?, " +
                     "id_lector = ?, " +
                     "fecha_reserva = ?, " +
                     "fecha_expiracion = ?, " +
                     "estado = ?, " +
                     "fecha_registro = ?, " +
                     "fecha_modificacion = ? " +
                     "WHERE id_reserva = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // 1. Foreign Keys
            ps.setInt(1, reserva.getIdLibro());
            ps.setInt(2, reserva.getIdLector());

            // 2. Fechas de negocio (date)
            ps.setDate(3, reserva.getFechaReserva());
            ps.setDate(4, reserva.getFechaExpiracion());

            // 3. Estado (enum)
            ps.setString(5, reserva.getEstado());

            // 4. Timestamps del sistema
            if (reserva.getFechaRegistro() != null) {
                ps.setTimestamp(6, reserva.getFechaRegistro());
            } else {
                ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
            }

            if (reserva.getFechaModificacion() != null) {
                ps.setTimestamp(7, reserva.getFechaModificacion());
            } else {
                ps.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
            }

            // 5. WHERE id_reserva
            ps.setInt(8, reserva.getIdReserva());

            int filasAfectadas = ps.executeUpdate();

            // Log para debugging
            System.out.println("Reserva actualizada: ID=" + reserva.getIdReserva() + 
                             ", Estado=" + reserva.getEstado() + 
                             ", Filas afectadas=" + filasAfectadas);

            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("ERROR al actualizar reserva ID " + reserva.getIdReserva() + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public boolean eliminar(int idReserva) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idReserva);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
}