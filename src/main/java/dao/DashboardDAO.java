/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.ConexionDB;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DashboardDAO {
    
    public Map<String, Object> obtenerKPIs() throws SQLException {
        String sql = "SELECT * FROM vista_dashboard_kpis";
        Map<String, Object> kpis = new HashMap<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                kpis.put("total_libros", rs.getInt("total_libros"));
                kpis.put("total_lectores", rs.getInt("total_lectores"));
                kpis.put("total_usuarios", rs.getInt("total_usuarios"));
                kpis.put("prestamos_activos", rs.getInt("prestamos_activos"));
                kpis.put("prestamos_atrasados", rs.getInt("prestamos_atrasados"));
                kpis.put("reservas_activas", rs.getInt("reservas_activas"));
                kpis.put("total_copias", rs.getInt("total_copias"));
                kpis.put("copias_disponibles", rs.getInt("copias_disponibles"));
                kpis.put("total_categorias", rs.getInt("total_categorias"));
            }
        }
        
        return kpis;
    }
    
    public Map<String, Object> obtenerEstadisticasGenerales() throws SQLException {
        Map<String, Object> stats = new HashMap<>();
        
        try (Connection conn = ConexionDB.getConnection()) {
            
            String sqlPrestamos = "SELECT COUNT(*) as total FROM prestamos";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlPrestamos)) {
                if (rs.next()) {
                    stats.put("total_prestamos_realizados", rs.getInt("total"));
                }
            }
            
            String sqlBajas = "SELECT COUNT(*) as total FROM bajas_libros";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBajas)) {
                if (rs.next()) {
                    stats.put("total_bajas", rs.getInt("total"));
                }
            }
            
            String sqlBloqueados = "SELECT COUNT(*) as total FROM usuarios WHERE bloqueado = 1";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlBloqueados)) {
                if (rs.next()) {
                    stats.put("usuarios_bloqueados", rs.getInt("total"));
                }
            }
            
            String sqlReservasTotal = "SELECT COUNT(*) as total FROM reservas";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlReservasTotal)) {
                if (rs.next()) {
                    stats.put("total_reservas_realizadas", rs.getInt("total"));
                }
            }
        }
        
        return stats;
    }
    
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
}