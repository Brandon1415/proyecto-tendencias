/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.ConexionDB;
import model.Lector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectorDAO {
    
    public List<Lector> listarTodos() throws SQLException {
        String sql = "SELECT * FROM lectores WHERE estado = 'ACTIVO' ORDER BY fecha_registro DESC";
        List<Lector> lectores = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lectores.add(mapearLector(rs));
            }
        }
        
        return lectores;
    }
    
    public boolean insertar(Lector lector) throws SQLException {
        String sql = "{CALL sp_insertar_lector(?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setString(1, lector.getNombre());
            cs.setString(2, lector.getApellido());
            cs.setString(3, lector.getCedula());
            cs.setString(4, lector.getCorreo());
            cs.setString(5, lector.getTelefono());
            cs.setString(6, lector.getDireccion());
            
            cs.execute();
            return true;
        }
    }
    
    public boolean actualizar(Lector lector) throws SQLException {
        String sql = "{CALL sp_actualizar_lector(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, lector.getIdLector());
            cs.setString(2, lector.getNombre());
            cs.setString(3, lector.getApellido());
            cs.setString(4, lector.getCedula());
            cs.setString(5, lector.getCorreo());
            cs.setString(6, lector.getTelefono());
            cs.setString(7, lector.getDireccion());
            cs.setString(8, lector.getEstado());
            
            cs.execute();
            return true;
        }
    }
    
    public boolean eliminar(int idLector) throws SQLException {
        String sql = "DELETE FROM lectores WHERE id_lector = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLector);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    public Lector obtenerPorId(int idLector) throws SQLException {
        String sql = "SELECT * FROM lectores WHERE id_lector = ?";
        Lector lector = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLector);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lector = mapearLector(rs);
                }
            }
        }
        
        return lector;
    }
    
    public Lector obtenerPorCedula(String cedula) throws SQLException {
        String sql = "SELECT * FROM lectores WHERE cedula = ?";
        Lector lector = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, cedula);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    lector = mapearLector(rs);
                }
            }
        }
        
        return lector;
    }
    
    public List<Lector> buscar(String criterio) throws SQLException {
        String sql = "SELECT * FROM lectores WHERE estado = 'ACTIVO' AND " +
                     "(nombre LIKE ? OR apellido LIKE ? OR cedula LIKE ?) " +
                     "ORDER BY nombre";
        List<Lector> lectores = new ArrayList<>();
        String busqueda = "%" + criterio + "%";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lectores.add(mapearLector(rs));
                }
            }
        }
        
        return lectores;
    }
    
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM lectores WHERE estado = 'ACTIVO'";
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
    
    
    private Lector mapearLector(ResultSet rs) throws SQLException {
        Lector lector = new Lector();
        lector.setIdLector(rs.getInt("id_lector"));
        lector.setNombre(rs.getString("nombre"));
        lector.setApellido(rs.getString("apellido"));
        lector.setCedula(rs.getString("cedula"));
        lector.setCorreo(rs.getString("correo"));
        lector.setTelefono(rs.getString("telefono"));
        lector.setDireccion(rs.getString("direccion"));
        lector.setEstado(rs.getString("estado"));
        lector.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        lector.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        return lector;
    }
}