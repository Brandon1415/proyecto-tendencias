/*
 * DAO para gestionar operaciones de Lectores
 * 
 */
package dao;

import config.ConexionDB;
import model.Lector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectorDAO {
    
    /**
     * Listar todos los lectores
     */
    public List<Lector> listarTodos() throws SQLException {
        String sql = "SELECT * FROM lectores ORDER BY nombre ASC";
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
    
    /**
     * Insertar un nuevo lector
     */
    public boolean insertar(Lector lector) throws SQLException {
        String sql = "INSERT INTO lectores (nombre, apellido, cedula, correo, telefono, direccion, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, lector.getNombre());
            ps.setString(2, lector.getApellido());
            ps.setString(3, lector.getCedula());
            ps.setString(4, lector.getCorreo());
            ps.setString(5, lector.getTelefono());
            ps.setString(6, lector.getDireccion());
            ps.setString(7, lector.getEstado() != null ? lector.getEstado() : "ACTIVO");
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * ✅ ACTUALIZAR LECTOR - EDITA LITERALMENTE TODO
     */
    public boolean actualizar(Lector lector) throws SQLException {
        String sql = "UPDATE lectores SET nombre = ?, apellido = ?, cedula = ?, " +
                     "correo = ?, telefono = ?, direccion = ?, estado = ?, fecha_registro = ? " +
                     "WHERE id_lector = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, lector.getNombre());
            ps.setString(2, lector.getApellido());
            ps.setString(3, lector.getCedula());
            ps.setString(4, lector.getCorreo());
            ps.setString(5, lector.getTelefono());
            ps.setString(6, lector.getDireccion());
            ps.setString(7, lector.getEstado());
            
            // ✅ Permitir editar fecha_registro también
            if (lector.getFechaRegistro() != null) {
                ps.setTimestamp(8, lector.getFechaRegistro());
            } else {
                ps.setNull(8, java.sql.Types.TIMESTAMP);
            }
            
            ps.setInt(9, lector.getIdLector());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * Eliminar un lector
     */
    public boolean eliminar(int idLector) throws SQLException {
        String sql = "DELETE FROM lectores WHERE id_lector = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLector);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * Obtener lector por ID
     */
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
    
    /**
     * Obtener lector por cédula
     */
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
    
    /**
     * Buscar lectores por criterio
     */
    public List<Lector> buscar(String criterio) throws SQLException {
        String sql = "SELECT * FROM lectores WHERE " +
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
    
    /**
     * Contar total de lectores
     */
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM lectores";
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
     * Mapear ResultSet a Lector
     */
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
        
        return lector;
    }
}