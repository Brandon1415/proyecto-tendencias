/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import dao.*;
import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * SERVLET ÚNICO que controla todo el Sistema de Gestión Bibliotecaria MR Books
 * Usa variables globales de navegación para controlar el flujo
 */
@WebServlet("/ControladorSistema")
public class ControladorSistema extends HttpServlet {
    
    // Variables globales de navegación - Páginas JSP
    private static final String LOGIN = "vistas/login.jsp";
    private static final String DASHBOARD = "vistas/dashboard.jsp";
    private static final String USUARIOS = "vistas/usuarios.jsp";
    private static final String LECTORES = "vistas/lectores.jsp";
    private static final String LIBROS = "vistas/libros.jsp";
    private static final String PRESTAMOS = "vistas/prestamos.jsp";
    private static final String REPORTES = "vistas/reportes.jsp";
    
    // DAOs
    private UsuarioDAO usuarioDAO;
    private LectorDAO lectorDAO;
    private LibroDAO libroDAO;
    private PrestamoDAO prestamoDAO;
    private ReservaDAO reservaDAO;
    private BajaLibroDAO bajaLibroDAO;
    private DashboardDAO dashboardDAO;
    
    @Override
    public void init() throws ServletException {
        // Inicializar DAOs
        usuarioDAO = new UsuarioDAO();
        lectorDAO = new LectorDAO();
        libroDAO = new LibroDAO();
        prestamoDAO = new PrestamoDAO();
        reservaDAO = new ReservaDAO();
        bajaLibroDAO = new BajaLibroDAO();
        dashboardDAO = new DashboardDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Procesa las peticiones GET y POST
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener la acción solicitada
        String action = request.getParameter("action");
        
        if (action == null || action.isEmpty()) {
            action = "login";
        }
        
        try {
            switch (action) {
                // ============================================
                // MÓDULO DE AUTENTICACIÓN
                // ============================================
                case "login":
                    mostrarLogin(request, response);
                    break;
                    
                case "autenticar":
                    autenticarUsuario(request, response);
                    break;
                    
                case "logout":
                    cerrarSesion(request, response);
                    break;
                
                // ============================================
                // MÓDULO DE DASHBOARD
                // ============================================
                case "dashboard":
                    mostrarDashboard(request, response);
                    break;
                
                // ============================================
                // MÓDULO DE USUARIOS (Solo Administrador)
                // ============================================
                case "listarUsuarios":
                    listarUsuarios(request, response);
                    break;
                    
                case "nuevoUsuario":
                    mostrarFormularioUsuario(request, response, "nuevo");
                    break;
                    
                case "editarUsuario":
                    mostrarFormularioUsuario(request, response, "editar");
                    break;
                    
                case "guardarUsuario":
                    guardarUsuario(request, response);
                    break;
                    
                case "desbloquearUsuario":
                    desbloquearUsuario(request, response);
                    break;
                
                case "eliminarUsuario":
                    eliminarUsuario(request, response);
                    break;
                
                // ============================================
                // MÓDULO DE LECTORES
                // ============================================
                case "listarLectores":
                    listarLectores(request, response);
                    break;
                    
                case "nuevoLector":
                    mostrarFormularioLector(request, response, "nuevo");
                    break;
                    
                case "editarLector":
                    mostrarFormularioLector(request, response, "editar");
                    break;
                    
                case "guardarLector":
                    guardarLector(request, response);
                    break;
                    
                case "eliminarLector":
                    eliminarLector(request, response);
                    break;
                    
                case "buscarLectores":
                    buscarLectores(request, response);
                    break;
                
                // ============================================
                // MÓDULO DE LIBROS
                // ============================================
                case "listarLibros":
                    listarLibros(request, response);
                    break;
                    
                case "nuevoLibro":
                    mostrarFormularioLibro(request, response, "nuevo");
                    break;
                    
                case "editarLibro":
                    mostrarFormularioLibro(request, response, "editar");
                    break;
                    
                case "guardarLibro":
                    guardarLibro(request, response);
                    break;
                    
                case "darBajaLibro":
                    darBajaLibro(request, response);
                    break;
                
                case "eliminarLibro":
                    eliminarLibro(request, response);
                    break;
                    
                case "buscarLibros":
                    buscarLibros(request, response);
                    break;
                
                // ============================================
                // MÓDULO DE PRÉSTAMOS
                // ============================================
                case "listarPrestamos":
                    listarPrestamos(request, response);
                    break;
                    
                case "nuevoPrestamo":
                    mostrarFormularioPrestamo(request, response);
                    break;
                    
                case "registrarPrestamo":
                    registrarPrestamo(request, response);
                    break;
                    
                case "registrarDevolucion":
                    registrarDevolucion(request, response);
                    break;
                    
                case "editarPrestamo":
                    mostrarFormularioEditarPrestamo(request, response);
                    break;
                
                case "actualizarPrestamo":
                    actualizarPrestamo(request, response);
                    break;
                
                case "cancelarPrestamo":
                    cancelarPrestamo(request, response);
                    break;
                
                case "eliminarPrestamo":
                    eliminarPrestamo(request, response);
                    break;
                    
                case "verHistorialLector":
                    verHistorialLector(request, response);
                    break;
                
                // ============================================
                // MÓDULO DE REPORTES
                // ============================================
                case "reportes":
                    mostrarReportes(request, response);
                    break;
                    
                case "reporteLibrosMasPrestados":
                    generarReporteLibrosMasPrestados(request, response);
                    break;
                    
                case "reportePrestamosAtrasados":
                    generarReportePrestamosAtrasados(request, response);
                    break;
                    
                case "reporteEstadisticasLectores":
                    generarReporteEstadisticasLectores(request, response);
                    break;
                
                // ============================================
                // MÓDULO DE RESERVAS
                // ============================================
                case "listarReservas":
                    listarReservas(request, response);
                    break;
                    
                case "nuevaReserva":
                    mostrarFormularioReserva(request, response);
                    break;
                    
                case "crearReserva":
                    crearReserva(request, response);
                    break;
                    
                case "cancelarReserva":
                    cancelarReserva(request, response);
                    break;
                    
                case "completarReserva":
                    completarReserva(request, response);
                    break;
                
                case "editarReserva":
                    mostrarFormularioEditarReserva(request, response);
                    break;
                
                case "actualizarReserva":
                    actualizarReserva(request, response);
                    break;
                
                case "eliminarReserva":
                    eliminarReserva(request, response);
                    break;
                    
                case "verHistorialBajas":
                    verHistorialBajas(request, response);
                    break;
                
                case "editarBaja":
                    mostrarFormularioEditarBaja(request, response);
                    break;
                
                case "actualizarBaja":
                    actualizarBaja(request, response);
                    break;
                
                case "eliminarBaja":
                    eliminarBaja(request, response);
                    break;
                
                default:
                    mostrarLogin(request, response);
                    break;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error de base de datos: " + e.getMessage());
            request.getRequestDispatcher("vistas/error.jsp").forward(request, response);
        }
    }
    
    // ============================================
    // MÉTODOS DE AUTENTICACIÓN
    // ============================================
    
    private void mostrarLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(LOGIN).forward(request, response);
    }
    
    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Verificar si el usuario está bloqueado
        if (usuarioDAO.estaBloqueado(email)) {
            request.setAttribute("error", "Su cuenta está bloqueada por 5 minutos debido a múltiples intentos fallidos.");
            request.getRequestDispatcher(LOGIN).forward(request, response);
            return;
        }
        
        // Intentar autenticar
        Usuario usuario = usuarioDAO.autenticar(email, password);
        
        if (usuario != null) {
            // Autenticación exitosa
            usuarioDAO.resetearIntentos(email);
            
            // Crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombreCompleto());
            session.setAttribute("rol", usuario.getRol());
            session.setAttribute("idUsuario", usuario.getIdUsuario());
            
            // Redirigir al dashboard
            response.sendRedirect("ControladorSistema?action=dashboard");
            
        } else {
            // Autenticación fallida
            Usuario usuarioExiste = usuarioDAO.autenticar(email, "dummy_check");
            
            if (usuarioExiste != null) {
                // El usuario existe pero la contraseña es incorrecta
                usuarioDAO.registrarIntentoFallido(email);
                
                if (usuarioExiste.esAdministrador()) {
                    request.setAttribute("error", "Credenciales incorrectas. Administrador: no será bloqueado.");
                } else {
                    request.setAttribute("error", "Credenciales incorrectas. Después de 3 intentos fallidos, su cuenta será bloqueada por 5 minutos.");
                }
            } else {
                request.setAttribute("error", "Usuario no encontrado o inactivo.");
            }
            
            request.getRequestDispatcher(LOGIN).forward(request, response);
        }
    }
    
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("ControladorSistema?action=login");
    }
    
    // ============================================
    // MÉTODOS DE DASHBOARD
    // ============================================
    
    private void mostrarDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        // Obtener KPIs
        Map<String, Object> kpis = dashboardDAO.obtenerKPIs();
        Map<String, Object> stats = dashboardDAO.obtenerEstadisticasGenerales();
        
        request.setAttribute("kpis", kpis);
        request.setAttribute("stats", stats);
        
        request.getRequestDispatcher(DASHBOARD).forward(request, response);
    }
    
    // ============================================
    // MÉTODOS DE USUARIOS
    // ============================================
    
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        List<Usuario> bloqueados = usuarioDAO.listarBloqueados();
        
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("bloqueados", bloqueados);
        request.getRequestDispatcher(USUARIOS).forward(request, response);
    }
    
    private void mostrarFormularioUsuario(HttpServletRequest request, HttpServletResponse response, String modo)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        if ("editar".equals(modo)) {
            int idUsuario = Integer.parseInt(request.getParameter("id"));
            Usuario usuario = usuarioDAO.obtenerPorId(idUsuario);
            request.setAttribute("usuario", usuario);
        }
        
        request.setAttribute("modo", modo);
        request.getRequestDispatcher("vistas/form_usuario.jsp").forward(request, response);
    }
    
    private void guardarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        String modo = request.getParameter("modo");
        Usuario usuario = new Usuario();
        
        if ("editar".equals(modo)) {
            usuario.setIdUsuario(Integer.parseInt(request.getParameter("id")));
        }
        
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setApellido(request.getParameter("apellido"));
        usuario.setEmail(request.getParameter("email"));
        usuario.setRol(request.getParameter("rol"));
        usuario.setEstado(request.getParameter("estado"));
        
        boolean exito;
        if ("nuevo".equals(modo)) {
            usuario.setPassword(request.getParameter("password"));
            exito = usuarioDAO.insertar(usuario);
        } else {
            exito = usuarioDAO.actualizar(usuario);
        }
        
        if (exito) {
            request.setAttribute("mensaje", "Usuario guardado exitosamente");
        } else {
            request.setAttribute("error", "Error al guardar usuario");
        }
        
        listarUsuarios(request, response);
    }
    
    private void desbloquearUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        int idUsuario = Integer.parseInt(request.getParameter("id"));
        boolean exito = usuarioDAO.desbloquearUsuario(idUsuario);
        
        if (exito) {
            request.setAttribute("mensaje", "Usuario desbloqueado exitosamente");
        }
        
        listarUsuarios(request, response);
    }
    
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        int idUsuario = Integer.parseInt(request.getParameter("id"));
        
        // Verificar que no se elimine a sí mismo
        HttpSession session = request.getSession();
        int idUsuarioActual = (int) session.getAttribute("idUsuario");
        
        if (idUsuario == idUsuarioActual) {
            request.setAttribute("error", "No puedes eliminar tu propia cuenta");
        } else {
            boolean exito = usuarioDAO.eliminar(idUsuario);
            
            if (exito) {
                request.setAttribute("mensaje", "Usuario eliminado exitosamente");
            } else {
                request.setAttribute("error", "Error al eliminar usuario");
            }
        }
        
        listarUsuarios(request, response);
    }
    
    // ============================================
    // MÉTODOS DE LECTORES
    // ============================================
    
    private void listarLectores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Lector> lectores = lectorDAO.listarTodos();
        request.setAttribute("lectores", lectores);
        request.getRequestDispatcher(LECTORES).forward(request, response);
    }
    
    private void mostrarFormularioLector(HttpServletRequest request, HttpServletResponse response, String modo)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        if ("editar".equals(modo)) {
            int idLector = Integer.parseInt(request.getParameter("id"));
            Lector lector = lectorDAO.obtenerPorId(idLector);
            request.setAttribute("lector", lector);
        }
        
        request.setAttribute("modo", modo);
        request.getRequestDispatcher("vistas/form_lector.jsp").forward(request, response);
    }
    
    private void guardarLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        String modo = request.getParameter("modo");
        Lector lector = new Lector();
        
        if ("editar".equals(modo)) {
            lector.setIdLector(Integer.parseInt(request.getParameter("id")));
            lector.setEstado(request.getParameter("estado"));
        }
        
        lector.setNombre(request.getParameter("nombre"));
        lector.setApellido(request.getParameter("apellido"));
        lector.setCedula(request.getParameter("cedula"));
        lector.setCorreo(request.getParameter("correo"));
        lector.setTelefono(request.getParameter("telefono"));
        lector.setDireccion(request.getParameter("direccion"));
        
        boolean exito;
        if ("nuevo".equals(modo)) {
            exito = lectorDAO.insertar(lector);
        } else {
            exito = lectorDAO.actualizar(lector);
        }
        
        if (exito) {
            request.setAttribute("mensaje", "Lector guardado exitosamente");
        } else {
            request.setAttribute("error", "Error al guardar lector");
        }
        
        listarLectores(request, response);
    }
    
    private void eliminarLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idLector = Integer.parseInt(request.getParameter("id"));
        
        try {
            // Verificar si el lector tiene préstamos asociados
            List<Map<String, Object>> prestamos = prestamoDAO.obtenerPorLector(idLector);
            
            if (prestamos != null && !prestamos.isEmpty()) {
                request.setAttribute("error", "No se puede eliminar el lector porque tiene préstamos asociados. " +
                                             "Total de préstamos: " + prestamos.size());
            } else {
                boolean exito = lectorDAO.eliminar(idLector);
                
                if (exito) {
                    request.setAttribute("mensaje", "Lector eliminado exitosamente");
                } else {
                    request.setAttribute("error", "Error al eliminar lector");
                }
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Error al eliminar lector: " + e.getMessage());
        }
        
        listarLectores(request, response);
    }
    
    private void buscarLectores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        String criterio = request.getParameter("criterio");
        List<Lector> lectores = lectorDAO.buscar(criterio);
        
        request.setAttribute("lectores", lectores);
        request.setAttribute("criterio", criterio);
        request.getRequestDispatcher(LECTORES).forward(request, response);
    }
    
    // ============================================
    // MÉTODOS DE LIBROS
    // ============================================
    
    private void listarLibros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Libro> libros = libroDAO.listarTodos();
        List<Categoria> categorias = libroDAO.listarCategorias();
        
        request.setAttribute("libros", libros);
        request.setAttribute("categorias", categorias);
        request.getRequestDispatcher(LIBROS).forward(request, response);
    }
    
    private void mostrarFormularioLibro(HttpServletRequest request, HttpServletResponse response, String modo)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Categoria> categorias = libroDAO.listarCategorias();
        request.setAttribute("categorias", categorias);
        
        if ("editar".equals(modo)) {
            int idLibro = Integer.parseInt(request.getParameter("id"));
            Libro libro = libroDAO.obtenerPorId(idLibro);
            request.setAttribute("libro", libro);
        }
        
        request.setAttribute("modo", modo);
        request.getRequestDispatcher("vistas/form_libro.jsp").forward(request, response);
    }
    
    private void guardarLibro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        String modo = request.getParameter("modo");
        Libro libro = new Libro();
        
        if ("editar".equals(modo)) {
            libro.setIdLibro(Integer.parseInt(request.getParameter("id")));
        }
        
        libro.setTitulo(request.getParameter("titulo"));
        libro.setAutor(request.getParameter("autor"));
        libro.setIsbn(request.getParameter("isbn"));
        libro.setIdCategoria(Integer.parseInt(request.getParameter("categoria")));
        libro.setEditorial(request.getParameter("editorial"));
        libro.setAnioPublicacion(Integer.parseInt(request.getParameter("anio")));
        libro.setCopiasTotales(Integer.parseInt(request.getParameter("copias")));
        
        boolean exito;
        if ("nuevo".equals(modo)) {
            exito = libroDAO.insertar(libro);
        } else {
            exito = libroDAO.actualizar(libro);
        }
        
        if (exito) {
            request.setAttribute("mensaje", "Libro guardado exitosamente");
        } else {
            request.setAttribute("error", "Error al guardar libro");
        }
        
        listarLibros(request, response);
    }
    
    private void darBajaLibro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idLibro = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");
        String motivo = request.getParameter("motivo");
        String descripcion = request.getParameter("descripcion");
        
        boolean exito = libroDAO.registrarBaja(idLibro, idUsuario, motivo, descripcion);
        
        if (exito) {
            request.setAttribute("mensaje", "Libro dado de baja exitosamente");
        }
        
        listarLibros(request, response);
    }
    
    private void eliminarLibro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        int idLibro = Integer.parseInt(request.getParameter("id"));
        
        try {
            boolean exito = libroDAO.eliminar(idLibro);
            
            if (exito) {
                request.setAttribute("mensaje", "Libro eliminado permanentemente");
            } else {
                request.setAttribute("error", "Error al eliminar libro");
            }
        } catch (SQLException e) {
            // Si hay error de FK constraint
            if (e.getMessage().contains("foreign key constraint")) {
                request.setAttribute("error", "No se puede eliminar el libro porque tiene préstamos/reservas asociadas. Use 'Dar de Baja' en su lugar.");
            } else {
                request.setAttribute("error", "Error al eliminar libro: " + e.getMessage());
            }
        }
        
        listarLibros(request, response);
    }
    
    private void buscarLibros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        String criterio = request.getParameter("criterio");
        List<Libro> libros = libroDAO.buscar(criterio);
        List<Categoria> categorias = libroDAO.listarCategorias();
        
        request.setAttribute("libros", libros);
        request.setAttribute("categorias", categorias);
        request.setAttribute("criterio", criterio);
        request.getRequestDispatcher(LIBROS).forward(request, response);
    }
    
    // ============================================
    // MÉTODOS DE PRÉSTAMOS
    // ============================================
    
    private void listarPrestamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Map<String, Object>> prestamos = prestamoDAO.listarHistorial();
        request.setAttribute("prestamos", prestamos);
        request.getRequestDispatcher(PRESTAMOS).forward(request, response);
    }
    
    private void mostrarFormularioPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Libro> libros = libroDAO.listarTodos();
        List<Lector> lectores = lectorDAO.listarTodos();
        
        request.setAttribute("libros", libros);
        request.setAttribute("lectores", lectores);
        request.getRequestDispatcher("vistas/form_prestamo.jsp").forward(request, response);
    }
    
    private void registrarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idLibro = Integer.parseInt(request.getParameter("libro"));
        int idLector = Integer.parseInt(request.getParameter("lector"));
        HttpSession session = request.getSession();
        int idUsuario = (int) session.getAttribute("idUsuario");
        
        boolean exito = prestamoDAO.registrarPrestamo(idLibro, idLector, idUsuario);
        
        if (exito) {
            request.setAttribute("mensaje", "Préstamo registrado exitosamente");
        } else {
            request.setAttribute("error", "Error al registrar préstamo");
        }
        
        listarPrestamos(request, response);
    }
    
    private void registrarDevolucion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idPrestamo = Integer.parseInt(request.getParameter("id"));
        boolean exito = prestamoDAO.registrarDevolucion(idPrestamo);
        
        if (exito) {
            request.setAttribute("mensaje", "Devolución registrada exitosamente");
        }
        
        listarPrestamos(request, response);
    }
    
    private void mostrarFormularioEditarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idPrestamo = Integer.parseInt(request.getParameter("id"));
        Prestamo prestamo = prestamoDAO.obtenerPorId(idPrestamo);
        
        if (prestamo != null) {
            // Cargar libros y lectores para los selects
            List<Libro> libros = libroDAO.listarTodos();
            List<Lector> lectores = lectorDAO.listarTodos();
            
            request.setAttribute("prestamo", prestamo);
            request.setAttribute("libros", libros);
            request.setAttribute("lectores", lectores);
            request.getRequestDispatcher("vistas/form_prestamo_edit.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Préstamo no encontrado");
            listarPrestamos(request, response);
        }
    }
    
    private void actualizarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        try {
            int idPrestamo = Integer.parseInt(request.getParameter("id"));
            int idLibro = Integer.parseInt(request.getParameter("libro"));
            int idLector = Integer.parseInt(request.getParameter("lector"));
            String fechaPrestamoStr = request.getParameter("fechaPrestamo");
            String fechaDevolucionStr = request.getParameter("fechaDevolucionEsperada");
            String observaciones = request.getParameter("observaciones");
            
            Prestamo prestamo = new Prestamo();
            prestamo.setIdPrestamo(idPrestamo);
            prestamo.setIdLibro(idLibro);
            prestamo.setIdLector(idLector);
            prestamo.setFechaPrestamo(java.sql.Date.valueOf(fechaPrestamoStr));
            prestamo.setFechaDevolucionEsperada(java.sql.Date.valueOf(fechaDevolucionStr));
            prestamo.setObservaciones(observaciones);
            
            boolean exito = prestamoDAO.actualizar(prestamo);
            
            if (exito) {
                request.setAttribute("mensaje", "Préstamo actualizado exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar préstamo");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        
        listarPrestamos(request, response);
    }
    
    private void cancelarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        try {
            int idPrestamo = Integer.parseInt(request.getParameter("id"));
            boolean exito = prestamoDAO.cancelarPrestamo(idPrestamo);
            
            if (exito) {
                request.setAttribute("mensaje", "Préstamo cancelado exitosamente");
            } else {
                request.setAttribute("error", "Error al cancelar préstamo");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Error al cancelar: " + e.getMessage());
        }
        
        listarPrestamos(request, response);
    }
    
    private void eliminarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        try {
            int idPrestamo = Integer.parseInt(request.getParameter("id"));
            boolean exito = prestamoDAO.eliminar(idPrestamo);
            
            if (exito) {
                request.setAttribute("mensaje", "Préstamo eliminado exitosamente");
            } else {
                request.setAttribute("error", "Error al eliminar préstamo");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        
        listarPrestamos(request, response);
    }
    
    private void verHistorialLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idLector = Integer.parseInt(request.getParameter("id"));
        List<Map<String, Object>> prestamos = prestamoDAO.obtenerPorLector(idLector);
        Lector lector = lectorDAO.obtenerPorId(idLector);
        
        request.setAttribute("prestamos", prestamos);
        request.setAttribute("lector", lector);
        request.getRequestDispatcher("vistas/historial_lector.jsp").forward(request, response);
    }
    
    // ============================================
    // MÉTODOS DE REPORTES
    // ============================================
    
    private void mostrarReportes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        request.getRequestDispatcher(REPORTES).forward(request, response);
    }
    
    private void generarReporteLibrosMasPrestados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Map<String, Object>> libros = libroDAO.obtenerMasPrestados(10);
        request.setAttribute("libros", libros);
        request.setAttribute("tipoReporte", "masPrestados");
        request.getRequestDispatcher(REPORTES).forward(request, response);
    }
    
    private void generarReportePrestamosAtrasados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Map<String, Object>> prestamos = prestamoDAO.listarAtrasados();
        request.setAttribute("prestamos", prestamos);
        request.setAttribute("tipoReporte", "atrasados");
        request.getRequestDispatcher(REPORTES).forward(request, response);
    }
    
    private void generarReporteEstadisticasLectores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Map<String, Object>> estadisticas = prestamoDAO.obtenerEstadisticasPorLector();
        request.setAttribute("estadisticas", estadisticas);
        request.setAttribute("tipoReporte", "estadisticasLectores");
        request.getRequestDispatcher(REPORTES).forward(request, response);
    }
    
    // ============================================
    // MÉTODOS DE RESERVAS
    // ============================================
    
    private void listarReservas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Map<String, Object>> reservas = reservaDAO.listarActivas();
        request.setAttribute("reservas", reservas);
        request.getRequestDispatcher("vistas/reservas.jsp").forward(request, response);
    }
    
    private void mostrarFormularioReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        List<Libro> libros = libroDAO.listarTodos();
        List<Lector> lectores = lectorDAO.listarTodos();
        
        request.setAttribute("libros", libros);
        request.setAttribute("lectores", lectores);
        request.getRequestDispatcher("vistas/form_reserva.jsp").forward(request, response);
    }
    
    private void crearReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idLibro = Integer.parseInt(request.getParameter("libro"));
        int idLector = Integer.parseInt(request.getParameter("lector"));
        
        boolean exito = reservaDAO.crearReserva(idLibro, idLector);
        
        if (exito) {
            request.setAttribute("mensaje", "Reserva creada exitosamente");
        } else {
            request.setAttribute("error", "Error al crear reserva");
        }
        
        listarReservas(request, response);
    }
    
    private void cancelarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idReserva = Integer.parseInt(request.getParameter("id"));
        boolean exito = reservaDAO.cancelarReserva(idReserva);
        
        if (exito) {
            request.setAttribute("mensaje", "Reserva cancelada exitosamente");
        }
        
        listarReservas(request, response);
    }
    
    private void completarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idReserva = Integer.parseInt(request.getParameter("id"));
        boolean exito = reservaDAO.completarReserva(idReserva);
        
        if (exito) {
            request.setAttribute("mensaje", "Reserva completada exitosamente");
        }
        
        listarReservas(request, response);
    }
    
    private void mostrarFormularioEditarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        int idReserva = Integer.parseInt(request.getParameter("id"));
        Reserva reserva = reservaDAO.obtenerPorId(idReserva);
        
        if (reserva != null) {
            // Cargar libros y lectores para los selects
            List<Libro> libros = libroDAO.listarTodos();
            List<Lector> lectores = lectorDAO.listarTodos();
            
            request.setAttribute("reserva", reserva);
            request.setAttribute("libros", libros);
            request.setAttribute("lectores", lectores);
            request.getRequestDispatcher("vistas/form_reserva_edit.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Reserva no encontrada");
            listarReservas(request, response);
        }
    }
    
    private void actualizarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        
        try {
            int idReserva = Integer.parseInt(request.getParameter("id"));
            int idLibro = Integer.parseInt(request.getParameter("libro"));
            int idLector = Integer.parseInt(request.getParameter("lector"));
            String fechaExpiracionStr = request.getParameter("fechaExpiracion");
            
            Reserva reserva = new Reserva();
            reserva.setIdReserva(idReserva);
            reserva.setIdLibro(idLibro);
            reserva.setIdLector(idLector);
            reserva.setFechaExpiracion(java.sql.Date.valueOf(fechaExpiracionStr));
            
            boolean exito = reservaDAO.actualizar(reserva);
            
            if (exito) {
                request.setAttribute("mensaje", "Reserva actualizada exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar reserva");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        
        listarReservas(request, response);
    }
    
    private void eliminarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        try {
            int idReserva = Integer.parseInt(request.getParameter("id"));
            boolean exito = reservaDAO.eliminar(idReserva);
            
            if (exito) {
                request.setAttribute("mensaje", "Reserva eliminada exitosamente");
            } else {
                request.setAttribute("error", "Error al eliminar reserva");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        
        listarReservas(request, response);
    }
    
    // ============================================
    // MÉTODOS DE HISTORIAL DE BAJAS
    // ============================================
    
    private void verHistorialBajas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        try {
            List<Map<String, Object>> bajas = bajaLibroDAO.listarTodas();
            System.out.println("DEBUG: Historial de bajas obtenido, registros: " + bajas.size());
            request.setAttribute("bajas", bajas);
            request.getRequestDispatcher("vistas/historial_bajas.jsp").forward(request, response);
        } catch (Exception e) {
            System.err.println("ERROR en verHistorialBajas: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener historial de bajas: " + e.getMessage());
            request.getRequestDispatcher("vistas/error.jsp").forward(request, response);
        }
    }
    
    private void mostrarFormularioEditarBaja(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        int idBaja = Integer.parseInt(request.getParameter("id"));
        BajaLibro baja = bajaLibroDAO.obtenerPorId(idBaja);
        
        if (baja != null) {
            request.setAttribute("baja", baja);
            request.getRequestDispatcher("vistas/form_baja.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registro de baja no encontrado");
            verHistorialBajas(request, response);
        }
    }
    
    private void actualizarBaja(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        try {
            int idBaja = Integer.parseInt(request.getParameter("id"));
            String motivo = request.getParameter("motivo");
            String descripcion = request.getParameter("descripcion");
            String fechaBajaStr = request.getParameter("fechaBaja");
            
            BajaLibro baja = new BajaLibro();
            baja.setIdBaja(idBaja);
            baja.setMotivo(motivo);
            baja.setDescripcion(descripcion);
            baja.setFechaBaja(java.sql.Date.valueOf(fechaBajaStr));
            
            boolean exito = bajaLibroDAO.actualizar(baja);
            
            if (exito) {
                request.setAttribute("mensaje", "Registro de baja actualizado exitosamente");
            } else {
                request.setAttribute("error", "Error al actualizar registro de baja");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        
        verHistorialBajas(request, response);
    }
    
    private void eliminarBaja(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        
        try {
            int idBaja = Integer.parseInt(request.getParameter("id"));
            boolean exito = bajaLibroDAO.eliminar(idBaja);
            
            if (exito) {
                request.setAttribute("mensaje", "Registro de baja eliminado exitosamente");
            } else {
                request.setAttribute("error", "Error al eliminar registro de baja");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        
        verHistorialBajas(request, response);
    }
    
    // ============================================
    // MÉTODOS DE UTILIDAD
    // ============================================
    
    /**
     * Valida que exista una sesión activa
     */
    private boolean validarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("ControladorSistema?action=login");
            return false;
        }
        return true;
    }
    
    /**
     * Valida que el usuario sea Administrador
     */
    private boolean esAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        
        HttpSession session = request.getSession();
        String rol = (String) session.getAttribute("rol");
        
        if (!"ADMINISTRADOR".equalsIgnoreCase(rol)) {
            response.sendRedirect("ControladorSistema?action=dashboard");
            return false;
        }
        return true;
    }
}