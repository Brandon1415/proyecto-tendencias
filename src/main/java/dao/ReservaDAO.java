/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public List<Map<String, Object>> listarActivas() throws SQLException {
        String sql = "SELECT * FROM vista_reservas_activas ORDER BY fecha_expiracion";
        List<Map<String, Object>> reservas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                reservas.add(mapearReservaVista(rs));
            }
        }
        
        return reservas;
    }
    
    public List<Map<String, Object>> listarPorLector(int idLector) throws SQLException {
        String sql = "SELECT * FROM vista_reservas_activas WHERE cedula = (SELECT cedula FROM lectores WHERE id_lector = ?)";
        List<Map<String, Object>> reservas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLector);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservas.add(mapearReservaVista(rs));
                }
            }
        }
        
        return reservas;
    }
    
    public int contarActivasPorLector(int idLector) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM reservas WHERE id_lector = ? AND estado = 'ACTIVA'";
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
                    reserva = mapearReserva(rs);
                }
            }
        }
        
        return reserva;
    }
    
        /**
     * Actualizar una reserva
     */
    public boolean actualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reservas SET id_libro = ?, id_lector = ?, fecha_expiracion = ? " +
                     "WHERE id_reserva = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reserva.getIdLibro());
            ps.setInt(2, reserva.getIdLector());
            ps.setDate(3, reserva.getFechaExpiracion());
            ps.setInt(4, reserva.getIdReserva());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    /**
     * Eliminar una reserva
     */
    public boolean eliminar(int idReserva) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id_reserva = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idReserva);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    private Reserva mapearReserva(ResultSet rs) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getInt("id_reserva"));
        reserva.setIdLibro(rs.getInt("id_libro"));
        reserva.setIdLector(rs.getInt("id_lector"));
        reserva.setFechaReserva(rs.getDate("fecha_reserva"));
        reserva.setFechaExpiracion(rs.getDate("fecha_expiracion"));
        reserva.setEstado(rs.getString("estado"));
        reserva.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        reserva.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        return reserva;
    }
    
    private Map<String, Object> mapearReservaVista(ResultSet rs) throws SQLException {
        Map<String, Object> reserva = new HashMap<>();
        reserva.put("id_reserva", rs.getInt("id_reserva"));
        reserva.put("libro", rs.getString("libro"));
        reserva.put("isbn", rs.getString("isbn"));
        reserva.put("lector", rs.getString("lector"));
        reserva.put("cedula", rs.getString("cedula"));
        reserva.put("telefono", rs.getString("telefono"));
        reserva.put("fecha_reserva", rs.getDate("fecha_reserva"));
        reserva.put("fecha_expiracion", rs.getDate("fecha_expiracion"));
        reserva.put("dias_restantes", rs.getInt("dias_restantes"));
        reserva.put("estado", rs.getString("estado"));
        return reserva;
    }
}