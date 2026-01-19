/*
 * DAO para gestionar operaciones de Libros
 * ✅ ACTUALIZADO: Sin tabla categorias, categoria es VARCHAR en libros
 */
package dao;

import config.ConexionDB;
import model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroDAO {
    
    /**
     * ✅ Listar todos los libros activos
     */
    public List<Libro> listarTodos() throws SQLException {
        String sql = "SELECT * FROM libros WHERE activo = 1 ORDER BY titulo";
        List<Libro> libros = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        }
        
        return libros;
    }
    
    /**
     * Insertar un nuevo libro en la BD
     */
    public boolean insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libros (titulo, autor, isbn, categoria, editorial, anio_publicacion, copias_totales, copias_disponibles, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getCategoria());
            ps.setString(5, libro.getEditorial());
            ps.setInt(6, libro.getAnioPublicacion());
            ps.setInt(7, libro.getCopiasTotales());
            ps.setInt(8, libro.getCopiasTotales());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * Actualizar datos de un libro existente
     */
    public boolean actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, categoria = ?, " +
                     "editorial = ?, anio_publicacion = ?, copias_totales = ?, copias_disponibles = ?, " +
                     "activo = ?, fecha_modificacion = NOW() " +
                     "WHERE id_libro = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Libro libroActual = obtenerPorId(libro.getIdLibro());

            if (libroActual == null) {
                throw new SQLException("Libro no encontrado");
            }

            int diferencia = libro.getCopiasTotales() - libroActual.getCopiasTotales();
            int copiasDisponiblesNuevas = libroActual.getCopiasDisponibles() + diferencia;

            if (copiasDisponiblesNuevas < 0) {
                copiasDisponiblesNuevas = 0;
            }

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getCategoria());
            ps.setString(5, libro.getEditorial());
            ps.setInt(6, libro.getAnioPublicacion());
            ps.setInt(7, libro.getCopiasTotales());
            ps.setInt(8, copiasDisponiblesNuevas);
            ps.setBoolean(9, libro.isActivo());
            ps.setInt(10, libro.getIdLibro());

            int filasAfectados = ps.executeUpdate();
            return filasAfectados > 0;
        }
    }
    
    /**
     * Dar de baja un libro (desactivarlo) y registrar el motivo
     */
    public boolean registrarBaja(int idLibro, int idUsuario, String motivo, String descripcion) throws SQLException {
        String sql = "INSERT INTO historial_bajas (id_libro, id_usuario, fecha_baja, motivo, descripcion) " +
                     "VALUES (?, ?, CURDATE(), ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            conn.setAutoCommit(false);
            
            try {
                ps.setInt(1, idLibro);
                ps.setInt(2, idUsuario);
                ps.setString(3, motivo);
                ps.setString(4, descripcion);
                
                ps.executeUpdate();
                
                String sqlDesactivar = "UPDATE libros SET activo = 0, fecha_modificacion = NOW() WHERE id_libro = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(sqlDesactivar)) {
                    ps2.setInt(1, idLibro);
                    ps2.executeUpdate();
                }
                
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
    /**
     * Obtener un libro específico por su ID
     */
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
    
    /**
     * Obtener un libro por su ISBN
     */
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
    
    /**
     * Buscar libros por criterio (título, autor, categoría o ISBN)
     */
    public List<Libro> buscar(String criterio) throws SQLException {
        String sql = "SELECT * FROM libros " +
                     "WHERE activo = 1 AND " +
                     "(titulo LIKE ? OR autor LIKE ? OR categoria LIKE ? OR isbn LIKE ?) " +
                     "ORDER BY titulo";
        List<Libro> libros = new ArrayList<>();
        
        String busqueda = "%" + criterio + "%";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);
            ps.setString(4, busqueda);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapearLibro(rs));
                }
            }
        }
        
        return libros;
    }
    
    /**
     * Listar libros de una categoría específica
     */
    public List<Libro> listarPorCategoria(String categoria) throws SQLException {
        String sql = "SELECT * FROM libros " +
                     "WHERE categoria = ? AND activo = 1 " +
                     "ORDER BY titulo";
        List<Libro> libros = new ArrayList<>();
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, categoria);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapearLibro(rs));
                }
            }
        }
        
        return libros;
    }
    
    /**
     * Obtener los libros más prestados
     */
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
                    libro.put("prestamos_completados", rs.getInt("prestamos_completados"));
                    libro.put("copias_totales", rs.getInt("copias_totales"));
                    libro.put("copias_disponibles", rs.getInt("copias_disponibles"));
                    resultado.add(libro);
                }
            }
        }
        
        return resultado;
    }

    /**
     * Contar el total de libros activos
     */
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
    
    /**
     * Eliminar un libro permanentemente
     */
    public boolean eliminar(int idLibro) throws SQLException {
        String sql = "DELETE FROM libros WHERE id_libro = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idLibro);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    /**
     * Mapear ResultSet a objeto Libro
     */
    private Libro mapearLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        
        libro.setIdLibro(rs.getInt("id_libro"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setIsbn(rs.getString("isbn"));
        libro.setCategoria(rs.getString("categoria"));
        libro.setEditorial(rs.getString("editorial"));
        libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
        libro.setCopiasTotales(rs.getInt("copias_totales"));
        libro.setCopiasDisponibles(rs.getInt("copias_disponibles"));
        libro.setActivo(rs.getBoolean("activo"));
        libro.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        libro.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
        
        return libro;
    }
}