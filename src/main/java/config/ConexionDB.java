/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL
 */
public class ConexionDB {
    
    // Configuración de la base de datos
        private static final String URL =
        "jdbc:mysql://localhost:3307/biblioteca_mrbooks"
        + "?useSSL=false"
        + "&serverTimezone=UTC"
        + "&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "12345678";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Constructor privado para evitar instanciación
    private ConexionDB() {
        throw new IllegalStateException("Clase de utilidad");
    }
    
    /**
     * Obtiene una conexión a la base de datos
     */
    public static Connection getConnection() throws SQLException {
        Connection conexion = null;
        
        try {
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver MySQL no encontrado");
            e.printStackTrace();
            throw new SQLException("Driver no encontrado: " + e.getMessage());
            
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos");
            e.printStackTrace();
            throw e;
        }
        
        return conexion;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void closeConnection(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Prueba la conexión a la base de datos
     */
    public static boolean probarConexion() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Prueba de conexión fallida: " + e.getMessage());
            return false;
        }
    }
}