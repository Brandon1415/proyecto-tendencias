/*
 * DAO para gestionar operaciones de Préstamos
 * 
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
        String sql = "SELECT p.*, " +
                     "l.titulo as libro, l.isbn, " +
                     "CONCAT(lec.nombre, ' ', lec.apellido) as lector, " +
                     "lec.cedula, " +
                     "CONCAT(u.nombre, ' ', u.apellido) as empleado, " +
                     "CASE " +
                     "  WHEN p.estado = 'ACTIVO' AND p.fecha_devolucion_esperada < CURDATE() THEN 'ATRASADO' " +
                     "  ELSE p.estado " +
                     "END as estado_actual, " +
                     "DATEDIFF(CURDATE(), p.fecha_devolucion_esperada) as dias_retraso " +
                     "FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN lectores lec ON p.id_lector = lec.id_lector " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "ORDER BY p.fecha_prestamo DESC";
        
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
        String sql = "SELECT p.*, " +
                     "l.titulo as libro, l.isbn, " +
                     "CONCAT(lec.nombre, ' ', lec.apellido) as lector, " +
                     "lec.cedula, " +
                     "CONCAT(u.nombre, ' ', u.apellido) as empleado " +
                     "FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN lectores lec ON p.id_lector = lec.id_lector " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "WHERE p.estado = 'ACTIVO' " +
                     "ORDER BY p.fecha_devolucion_esperada";
        
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
        String sql = "SELECT p.*, " +
                     "l.titulo as libro, l.isbn, " +
                     "CONCAT(lec.nombre, ' ', lec.apellido) as lector, " +
                     "lec.cedula, " +
                     "CONCAT(u.nombre, ' ', u.apellido) as empleado " +
                     "FROM prestamos p " +
                     "JOIN libros l ON p.id_libro = l.id_libro " +
                     "JOIN lectores lec ON p.id_lector = lec.id_lector " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "WHERE p.estado = 'ATRASADO' " +
                     "ORDER BY p.fecha_devolucion_esperada DESC";
        
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
       String sql = "SELECT p.*, " +
                    "l.titulo as libro, l.isbn, " +
                    "CONCAT(lec.nombre, ' ', lec.apellido) as lector, " +
                    "lec.cedula, " +
                    "CONCAT(u.nombre, ' ', u.apellido) as empleado, " +
                    "CASE " +
                    "  WHEN p.estado = 'ACTIVO' AND p.fecha_devolucion_esperada < CURDATE() THEN 'ATRASADO' " +
                    "  ELSE p.estado " +
                    "END as estado_actual, " +
                    "DATEDIFF(CURDATE(), p.fecha_devolucion_esperada) as dias_retraso " +
                    "FROM prestamos p " +
                    "JOIN libros l ON p.id_libro = l.id_libro " +
                    "JOIN lectores lec ON p.id_lector = lec.id_lector " +
                    "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                    "WHERE p.id_lector = ? " +
                    "ORDER BY p.fecha_prestamo DESC";

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
    
    public boolean cancelarPrestamo(int idPrestamo) throws SQLException {
        String sql = "{CALL sp_cancelar_prestamo(?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idPrestamo);
            cs.execute();
            return true;
        }
    }
    
    /**
     * ✅ ACTUALIZAR PRESTAMO - EDITA LITERALMENTE TODO
     */
    public boolean actualizar(Prestamo prestamo) throws SQLException {
        String sql = "UPDATE prestamos SET id_libro = ?, id_lector = ?, id_usuario = ?, " +
                     "fecha_prestamo = ?, fecha_devolucion_esperada = ?, fecha_devolucion_real = ?, " +
                     "estado = ?, observaciones = ?, fecha_modificacion = NOW() " +
                     "WHERE id_prestamo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getIdLibro());
            ps.setInt(2, prestamo.getIdLector());
            ps.setInt(3, prestamo.getIdUsuario());
            ps.setDate(4, prestamo.getFechaPrestamo());
            ps.setDate(5, prestamo.getFechaDevolucionEsperada());
            
            if (prestamo.getFechaDevolucionReal() != null) {
                ps.setDate(6, prestamo.getFechaDevolucionReal());
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }
            
            ps.setString(7, prestamo.getEstado());
            ps.setString(8, prestamo.getObservaciones());
            ps.setInt(9, prestamo.getIdPrestamo());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
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
        prestamo.put("id_libro", rs.getInt("id_libro"));
        prestamo.put("id_lector", rs.getInt("id_lector"));
        prestamo.put("id_usuario", rs.getInt("id_usuario"));
        prestamo.put("libro", rs.getString("libro"));
        prestamo.put("isbn", rs.getString("isbn"));
        prestamo.put("lector", rs.getString("lector"));
        prestamo.put("cedula", rs.getString("cedula"));
        prestamo.put("empleado", rs.getString("empleado"));
        prestamo.put("fecha_prestamo", rs.getDate("fecha_prestamo"));
        prestamo.put("fecha_devolucion_esperada", rs.getDate("fecha_devolucion_esperada"));
        prestamo.put("fecha_devolucion_real", rs.getDate("fecha_devolucion_real"));
        prestamo.put("observaciones", rs.getString("observaciones"));
        prestamo.put("fecha_modificacion", rs.getTimestamp("fecha_modificacion"));
        prestamo.put("estado", rs.getString("estado"));
        prestamo.put("dias_retraso", rs.getInt("dias_retraso"));
        
        return prestamo;
    }
}