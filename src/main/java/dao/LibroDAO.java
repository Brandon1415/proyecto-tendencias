/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.ConexionDB;
import model.Libro;
import model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroDAO {
    
    public List<Libro> listarTodos() throws SQLException {
        String sql = "SELECT * FROM vista_libros_estado WHERE estado_libro != 'BAJA' ORDER BY titulo";
        List<Libro> libros = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(mapearLibroVista(rs));
            }
        }
        
        return libros;
    }
    
    public boolean insertar(Libro libro) throws SQLException {
        String sql = "{CALL sp_insertar_libro(?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setString(1, libro.getTitulo());
            cs.setString(2, libro.getAutor());
            cs.setString(3, libro.getIsbn());
            cs.setInt(4, libro.getIdCategoria());
            cs.setString(5, libro.getEditorial());
            cs.setInt(6, libro.getAnioPublicacion());
            cs.setInt(7, libro.getCopiasTotales());
            
            cs.execute();
            return true;
        }
    }
    
    public boolean actualizar(Libro libro) throws SQLException {
        String sql = "{CALL sp_actualizar_libro(?, ?, ?, ?, ?, ?, ?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, libro.getIdLibro());
            cs.setString(2, libro.getTitulo());
            cs.setString(3, libro.getAutor());
            cs.setString(4, libro.getIsbn());
            cs.setInt(5, libro.getIdCategoria());
            cs.setString(6, libro.getEditorial());
            cs.setInt(7, libro.getAnioPublicacion());
            cs.setInt(8, libro.getCopiasTotales());
            
            cs.execute();
            return true;
        }
    }
    
    public boolean registrarBaja(int idLibro, int idUsuario, String motivo, String descripcion) throws SQLException {
        String sql = "{CALL sp_registrar_baja_libro(?, ?, ?, ?)}";
        
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {
            
            cs.setInt(1, idLibro);
            cs.setInt(2, idUsuario);
            cs.setString(3, motivo);
            cs.setString(4, descripcion);
            
            cs.execute();
            return true;
        }
    }
    
    public Libro obtenerPorId(int idLibro) throws SQLException {
        String sql = "SELECT * FROM libros WHERE id_libro = ?";
        Libro libro = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLibro);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    libro = mapearLibro(rs);
                }
            }
        }
        
        return libro;
    }
    
    public Libro obtenerPorIsbn(String isbn) throws SQLException {
        String sql = "SELECT * FROM libros WHERE isbn = ?";
        Libro libro = null;
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, isbn);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    libro = mapearLibro(rs);
                }
            }
        }
        
        return libro;
    }
    
    public List<Libro> buscar(String criterio) throws SQLException {
        String sql = "SELECT * FROM vista_libros_estado WHERE estado_libro != 'BAJA' AND " +
                     "(titulo LIKE ? OR autor LIKE ? OR isbn LIKE ?) " +
                     "ORDER BY titulo";
        List<Libro> libros = new ArrayList<>();
        String busqueda = "%" + criterio + "%";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapearLibroVista(rs));
                }
            }
        }
        
        return libros;
    }
    
    public List<Libro> listarPorCategoria(int idCategoria) throws SQLException {
        String sql = "SELECT * FROM libros WHERE id_categoria = ? AND activo = 1 ORDER BY titulo";
        List<Libro> libros = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCategoria);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapearLibro(rs));
                }
            }
        }
        
        return libros;
    }
    
    public List<Map<String, Object>> obtenerMasPrestados(int limite) throws SQLException {
        String sql = "SELECT * FROM vista_libros_mas_prestados LIMIT ?";
        List<Map<String, Object>> resultado = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limite);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> libro = new HashMap<>();
                    libro.put("id_libro", rs.getInt("id_libro"));
                    libro.put("titulo", rs.getString("titulo"));
                    libro.put("autor", rs.getString("autor"));
                    libro.put("isbn", rs.getString("isbn"));
                    libro.put("categoria", rs.getString("categoria"));
                    libro.put("total_prestamos", rs.getInt("total_prestamos"));
                    libro.put("copias_totales", rs.getInt("copias_totales"));
                    libro.put("copias_disponibles", rs.getInt("copias_disponibles"));
                    resultado.add(libro);
                }
            }
        }
        
        return resultado;
    }
    
    public List<Categoria> listarCategorias() throws SQLException {
        String sql = "SELECT * FROM categorias WHERE estado = 'ACTIVO' ORDER BY nombre";
        List<Categoria> categorias = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categoria.setEstado(rs.getString("estado"));
                categoria.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                categorias.add(categoria);
            }
        }
        
        return categorias;
    }
    
    public List<Map<String, Object>> obtenerHistorialBajas() throws SQLException {
        String sql = "SELECT * FROM vista_historial_bajas";
        List<Map<String, Object>> bajas = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> baja = new HashMap<>();
                baja.put("id_baja", rs.getInt("id_baja"));
                baja.put("libro", rs.getString("libro"));
                baja.put("autor", rs.getString("autor"));
                baja.put("isbn", rs.getString("isbn"));
                baja.put("motivo", rs.getString("motivo"));
                baja.put("descripcion", rs.getString("descripcion"));
                baja.put("registrado_por", rs.getString("registrado_por"));
                baja.put("fecha_baja", rs.getDate("fecha_baja"));
                bajas.add(baja);
            }
        }
        
        return bajas;
    }
    
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM libros WHERE activo = 1";
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
    
    public boolean eliminar(int idLibro) throws SQLException {
    String sql = "DELETE FROM libros WHERE id_libro = ?";
    
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
        
             ps.setInt(1, idLibro);
              int filasAfectadas = ps.executeUpdate();
              return filasAfectadas > 0;
         }
}
    
    
    private Libro mapearLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setIdLibro(rs.getInt("id_libro"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setIdCategoria(rs.getInt("id_categoria"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
        libro.setCopiasTotales(rs.getInt("copias_totales"));
        libro.setCopiasDisponibles(rs.getInt("copias_disponibles"));
        libro.setActivo(rs.getBoolean("activo"));
        libro.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        libro.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        return libro;
    }
    
    private Libro mapearLibroVista(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setIdLibro(rs.getInt("id_libro"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setNombreCategoria(rs.getString("categoria"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
        libro.setCopiasTotales(rs.getInt("copias_totales"));
        libro.setCopiasDisponibles(rs.getInt("copias_disponibles"));
        libro.setActivo(true);
        return libro;
    }
}