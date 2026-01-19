/*
 * DAO para gestionar el Dashboard
 * 
 */
package dao;

import config.ConexionDB;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {
    
    /**
     * Obtener KPIs principales del dashboard
     */
    public Map<String, Object> obtenerKPIs() throws SQLException {
        String sql = "SELECT * FROM vista_dashboard_kpis";
        Map<String, Object> kpis = new HashMap<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                kpis.put("total_libros", rs.getInt("total_libros"));
                kpis.put("total_lectores", rs.getInt("total_lectores"));
                kpis.put("prestamos_activos", rs.getInt("prestamos_activos"));
                kpis.put("prestamos_atrasados", rs.getInt("prestamos_atrasados"));
                kpis.put("reservas_pendientes", rs.getInt("reservas_pendientes"));
                kpis.put("usuarios_activos", rs.getInt("usuarios_activos"));
            }
        }
        
        return kpis;
    }
    
    /**
     * Obtener estadísticas generales del dashboard
     */
    public Map<String, Object> obtenerEstadisticasGenerales() throws SQLException {
        Map<String, Object> stats = new HashMap<>();
        
        try (Connection conn = ConexionDB.getConnection()) {
            
            // Total de préstamos realizados
            String sqlTotalPrestamos = "SELECT COUNT(*) as total FROM prestamos";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlTotalPrestamos)) {
                if (rs.next()) {
                    stats.put("total_prestamos_realizados", rs.getInt("total"));
                }
            }
            
            // Préstamos completados
            String sqlPrestamosCompletados = "SELECT COUNT(*) as total FROM prestamos WHERE estado = 'DEVUELTO'";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlPrestamosCompletados)) {
                if (rs.next()) {
                    stats.put("prestamos_completados", rs.getInt("total"));
                }
            }
            
            // Préstamos cancelados
            String sqlPrestamosCancelados = "SELECT COUNT(*) as total FROM prestamos WHERE estado = 'CANCELADO'";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlPrestamosCancelados)) {
                if (rs.next()) {
                    stats.put("prestamos_cancelados", rs.getInt("total"));
                }
            }
            
            // Historial de bajas
            String sqlBajas = "SELECT COUNT(*) as total FROM historial_bajas";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBajas)) {
                if (rs.next()) {
                    stats.put("total_bajas", rs.getInt("total"));
                }
            }
            
            // Usuarios bloqueados
            String sqlBloqueados = "SELECT COUNT(*) as total FROM usuarios WHERE estado = 'BLOQUEADO'";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBloqueados)) {
                if (rs.next()) {
                    stats.put("usuarios_bloqueados", rs.getInt("total"));
                }
            }
            
            // Total de reservas
            String sqlReservasTotal = "SELECT COUNT(*) as total FROM reservas";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlReservasTotal)) {
                if (rs.next()) {
                    stats.put("total_reservas_realizadas", rs.getInt("total"));
                }
            }
            
            // Reservas completadas
            String sqlReservasCompletadas = "SELECT COUNT(*) as total FROM reservas WHERE estado = 'COMPLETADA'";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlReservasCompletadas)) {
                if (rs.next()) {
                    stats.put("reservas_completadas", rs.getInt("total"));
                }
            }
            
            // Total de copias
            String sqlTotalCopias = "SELECT SUM(copias_totales) as total FROM libros WHERE activo = 1";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlTotalCopias)) {
                if (rs.next()) {
                    Integer total = rs.getInt("total");
                    stats.put("total_copias", total != null ? total : 0);
                }
            }
            
            // Copias disponibles
            String sqlCopiasDisponibles = "SELECT SUM(copias_disponibles) as total FROM libros WHERE activo = 1";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlCopiasDisponibles)) {
                if (rs.next()) {
                    Integer total = rs.getInt("total");
                    stats.put("copias_disponibles", total != null ? total : 0);
                }
            }
            
        }
        
        return stats;
    }
    
    /**
     * Obtener estadísticas por período
     */
    public Map<String, Object> obtenerEstadisticasPorPeriodo(String periodo) throws SQLException {
        Map<String, Object> stats = new HashMap<>();
        String condicion = "";
        
        switch(periodo.toUpperCase()) {
            case "HOY":
                condicion = "DATE(fecha_prestamo) = CURDATE()";
                break;
            case "SEMANA":
                condicion = "YEARWEEK(fecha_prestamo, 1) = YEARWEEK(CURDATE(), 1)";
                break;
            case "MES":
                condicion = "MONTH(fecha_prestamo) = MONTH(CURDATE()) AND YEAR(fecha_prestamo) = YEAR(CURDATE())";
                break;
            case "AÑO":
                condicion = "YEAR(fecha_prestamo) = YEAR(CURDATE())";
                break;
            default:
                condicion = "1=1";
        }
        
        String sql = "SELECT COUNT(*) as total FROM prestamos WHERE " + condicion;
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                stats.put("prestamos_periodo", rs.getInt("total"));
            }
        }
        
        return stats;
    }
    
    /**
     * Obtener libros más prestados
     */
    public Map<String, Object> obtenerLibrosMasPrestados(int limite) throws SQLException {
        String sql = "SELECT * FROM vista_libros_mas_prestados LIMIT " + limite;
        Map<String, Object> resultado = new HashMap<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            resultado.put("libros_populares", rs);
        }
        
        return resultado;
    }
}