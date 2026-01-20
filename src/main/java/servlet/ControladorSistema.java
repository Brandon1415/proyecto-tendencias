/*
 * SERVLET ÚNICO que controla todo el Sistema de Gestión Bibliotecaria MR Books
 * ✅ VERSIÓN CORREGIDA - Sin intentos_fallidos ni referencias a categorías como tabla
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

@WebServlet("/ControladorSistema")
public class ControladorSistema extends HttpServlet {
    
    private static final String LOGIN = "index.jsp";
    private static final String DASHBOARD = "vistas/dashboard.jsp";
    private static final String USUARIOS = "vistas/usuarios.jsp";
    private static final String LECTORES = "vistas/lectores.jsp";
    private static final String LIBROS = "vistas/libros.jsp";
    private static final String PRESTAMOS = "vistas/prestamos.jsp";
    private static final String REPORTES = "vistas/reportes.jsp";
    
    private UsuarioDAO usuarioDAO;
    private LectorDAO lectorDAO;
    private LibroDAO libroDAO;
    private PrestamoDAO prestamoDAO;
    private ReservaDAO reservaDAO;
    private BajaLibroDAO bajaLibroDAO;
    private DashboardDAO dashboardDAO;
    
    @Override
    public void init() throws ServletException {
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
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) action = "login";
        
        try {
            switch (action) {
                case "login": mostrarLogin(request, response); break;
                case "autenticar": autenticarUsuario(request, response); break;
                case "logout": cerrarSesion(request, response); break;
                case "dashboard": mostrarDashboard(request, response); break;
                case "listarUsuarios": listarUsuarios(request, response); break;
                case "nuevoUsuario": mostrarFormularioUsuario(request, response, "nuevo"); break;
                case "editarUsuario": mostrarFormularioUsuario(request, response, "editar"); break;
                case "guardarUsuario": guardarUsuario(request, response); break;
                case "desbloquearUsuario": desbloquearUsuario(request, response); break;
                case "eliminarUsuario": eliminarUsuario(request, response); break;
                case "listarLectores": listarLectores(request, response); break;
                case "nuevoLector": mostrarFormularioLector(request, response, "nuevo"); break;
                case "editarLector": mostrarFormularioLector(request, response, "editar"); break;
                case "guardarLector": guardarLector(request, response); break;
                case "eliminarLector": eliminarLector(request, response); break;
                case "buscarLectores": buscarLectores(request, response); break;
                case "listarLibros": listarLibros(request, response); break;
                case "nuevoLibro": mostrarFormularioLibro(request, response, "nuevo"); break;
                case "editarLibro": mostrarFormularioLibro(request, response, "editar"); break;
                case "guardarLibro": guardarLibro(request, response); break;
                case "darBajaLibro": darBajaLibro(request, response); break;
                case "eliminarLibro": eliminarLibro(request, response); break;
                case "buscarLibros": buscarLibros(request, response); break;
                case "listarPrestamos": listarPrestamos(request, response); break;
                case "nuevoPrestamo": mostrarFormularioPrestamo(request, response); break;
                case "registrarPrestamo": registrarPrestamo(request, response); break;
                case "registrarDevolucion": registrarDevolucion(request, response); break;
                case "editarPrestamo": mostrarFormularioEditarPrestamo(request, response); break;
                case "actualizarPrestamo": actualizarPrestamo(request, response); break;
                case "cancelarPrestamo": cancelarPrestamo(request, response); break;
                case "eliminarPrestamo": eliminarPrestamo(request, response); break;
                case "verHistorialBajas": verHistorialBajas(request, response); break;
                case "verHistorialLector": verHistorialLector(request, response); break;
                case "reportes": mostrarReportes(request, response); break;
                case "reporteLibrosMasPrestados": generarReporteLibrosMasPrestados(request, response); break;
                case "reportePrestamosAtrasados": generarReportePrestamosAtrasados(request, response); break;
                case "reporteEstadisticasLectores": generarReporteEstadisticasLectores(request, response); break;
                case "listarReservas": listarReservas(request, response); break;
                case "nuevaReserva": mostrarFormularioReserva(request, response); break;
                case "crearReserva": crearReserva(request, response); break;
                case "cancelarReserva": cancelarReserva(request, response); break;
                case "completarReserva": completarReserva(request, response); break;
                case "editarReserva": mostrarFormularioEditarReserva(request, response); break;
                case "actualizarReserva": actualizarReserva(request, response); break;
                case "eliminarReserva": eliminarReserva(request, response); break;
                case "editarBaja": mostrarFormularioEditarBaja(request, response); break;
                case "actualizarBaja": actualizarBaja(request, response); break;
                case "eliminarBaja": eliminarBaja(request, response); break;
                default: mostrarLogin(request, response); break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error de base de datos: " + e.getMessage());
            try {
                request.getRequestDispatcher("vistas/error.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
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

        // 1. Verificar si el usuario está bloqueado
        BloqueoIntento bloqueoControl = BloqueoIntento.getInstance();

        if (bloqueoControl.estaBloqueado(email)) {
            int tiempoRestante = bloqueoControl.getTiempoRestanteBloqueo(email);
            request.setAttribute("error", 
                String.format("Cuenta bloqueada por 5 minutos. Tiempo restante: %d:%02d", 
                    tiempoRestante / 60, tiempoRestante % 60));
            request.setAttribute("email", email);
            request.setAttribute("bloqueado", true);
            request.setAttribute("tiempoRestante", tiempoRestante);
            request.getRequestDispatcher(LOGIN).forward(request, response);
            return;
        }

        // 2. Intentar autenticar
        Usuario usuario = usuarioDAO.autenticar(email, password);

        if (usuario != null) {
            // 3. Login exitoso - resetear intentos
            bloqueoControl.resetearIntentos(email);

            // Configurar sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombreCompleto());
            session.setAttribute("rol", usuario.getRol());
            session.setAttribute("idUsuario", usuario.getIdUsuario());

            response.sendRedirect("ControladorSistema?action=dashboard");
        } else {
            // 4. Login fallido - registrar intento
            boolean bloqueadoAhora = bloqueoControl.registrarIntentoFallido(email);
            int intentosRestantes = bloqueoControl.getIntentosRestantes(email);

            if (bloqueadoAhora) {
                // Acaba de ser bloqueado
                request.setAttribute("error", 
                    "Demasiados intentos fallidos. Tu cuenta ha sido bloqueada por 5 minutos.");
                request.setAttribute("email", email);
                request.setAttribute("bloqueado", true);
                request.setAttribute("tiempoRestante", 300); // 5 minutos en segundos
            } else {
                // Aún tiene intentos
                if (intentosRestantes > 0) {
                    request.setAttribute("error", 
                        String.format("Credenciales incorrectas. Intentos restantes: %d", 
                            intentosRestantes));
                    request.setAttribute("intentosRestantes", intentosRestantes);
                }
            }

            // Mantener el email en el formulario
            request.setAttribute("email", email);
            request.getRequestDispatcher(LOGIN).forward(request, response);
        }
    }
    
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Cerrando sesión del usuario: " + session.getAttribute("nombreUsuario"));
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
            request.getSession().setAttribute("mensaje", "Usuario guardado exitosamente");
        } else {
            request.getSession().setAttribute("error", "Error al guardar usuario");
        }
        
        response.sendRedirect("ControladorSistema?action=listarUsuarios");
    }
    
    private void desbloquearUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        int idUsuario = Integer.parseInt(request.getParameter("id"));
        boolean exito = usuarioDAO.desbloquearUsuario(idUsuario);
        if (exito) request.getSession().setAttribute("mensaje", "Usuario desbloqueado exitosamente");
        response.sendRedirect("ControladorSistema?action=listarUsuarios");
    }
    
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        int idUsuario = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        int idUsuarioActual = (int) session.getAttribute("idUsuario");
        if (idUsuario == idUsuarioActual) {
            request.getSession().setAttribute("error", "No puedes eliminar tu propia cuenta");
        } else {
            boolean exito = usuarioDAO.eliminar(idUsuario);
            if (exito) request.getSession().setAttribute("mensaje", "Usuario eliminado exitosamente");
            else request.getSession().setAttribute("error", "Error al eliminar usuario");
        }
        response.sendRedirect("ControladorSistema?action=listarUsuarios");
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
            }

            lector.setNombre(request.getParameter("nombre"));
            lector.setApellido(request.getParameter("apellido"));
            lector.setCedula(request.getParameter("cedula"));
            lector.setCorreo(request.getParameter("correo"));
            lector.setTelefono(request.getParameter("telefono"));
            lector.setDireccion(request.getParameter("direccion"));
            lector.setEstado(request.getParameter("estado"));

            // ✅ CAPTURAR Y PRESERVAR fecha_registro (solo en edición)
            if ("editar".equals(modo)) {
                String fechaRegistroStr = request.getParameter("fechaRegistro");
                if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
                    // Formato: "2024-01-15T10:30" → "2024-01-15 10:30:00"
                    String fechaRegistroSQL = fechaRegistroStr.replace("T", " ");
                    lector.setFechaRegistro(java.sql.Timestamp.valueOf(fechaRegistroSQL + ":00"));
                }
            }

            boolean exito = "nuevo".equals(modo) ? lectorDAO.insertar(lector) : lectorDAO.actualizar(lector);

            if (exito) {
                request.getSession().setAttribute("mensaje", "Lector guardado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al guardar lector");
            }

            response.sendRedirect("ControladorSistema?action=listarLectores");
        }
    
    private void eliminarLector(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        int idLector = Integer.parseInt(request.getParameter("id"));
        try {
            List<Map<String, Object>> prestamos = prestamoDAO.obtenerPorLector(idLector);
            if (prestamos != null && !prestamos.isEmpty()) {
                request.getSession().setAttribute("error", "No se puede eliminar el lector porque tiene préstamos asociados. Total: " + prestamos.size());
            } else {
                boolean exito = lectorDAO.eliminar(idLector);
                if (exito) request.getSession().setAttribute("mensaje", "Lector eliminado exitosamente");
                else request.getSession().setAttribute("error", "Error al eliminar lector");
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Error al eliminar lector: " + e.getMessage());
        }
        response.sendRedirect("ControladorSistema?action=listarLectores");
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
        request.setAttribute("libros", libros);
        request.getRequestDispatcher(LIBROS).forward(request, response);
    }
    
    private void mostrarFormularioLibro(HttpServletRequest request, HttpServletResponse response, String modo)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
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
            libro.setCategoria(request.getParameter("categoria"));
            libro.setEditorial(request.getParameter("editorial"));
            libro.setAnioPublicacion(Integer.parseInt(request.getParameter("anio")));
            libro.setCopiasTotales(Integer.parseInt(request.getParameter("copias")));

            // ✅ Capturar el estado "Activo" (solo en edición)
            if ("editar".equals(modo)) {
                String activoParam = request.getParameter("activo");
                boolean activo = "true".equals(activoParam);
                libro.setActivo(activo);
            } else {
                // En nuevo, siempre se crea como activo
                libro.setActivo(true);
            }

            boolean exito = "nuevo".equals(modo) ? libroDAO.insertar(libro) : libroDAO.actualizar(libro);

            if (exito) {
                request.getSession().setAttribute("mensaje", "Libro guardado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al guardar libro");
            }

            response.sendRedirect("ControladorSistema?action=listarLibros");
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
        if (exito) request.getSession().setAttribute("mensaje", "Libro dado de baja exitosamente");
        response.sendRedirect("ControladorSistema?action=listarLibros");
    }
    
    private void eliminarLibro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        int idLibro = Integer.parseInt(request.getParameter("id"));
        try {
            boolean exito = libroDAO.eliminar(idLibro);
            if (exito) request.getSession().setAttribute("mensaje", "Libro eliminado permanentemente");
            else request.getSession().setAttribute("error", "Error al eliminar libro");
        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint")) {
                request.getSession().setAttribute("error", "No se puede eliminar el libro porque tiene préstamos/reservas asociadas. Use 'Dar de Baja' en su lugar.");
            } else {
                request.getSession().setAttribute("error", "Error al eliminar libro: " + e.getMessage());
            }
        }
        response.sendRedirect("ControladorSistema?action=listarLibros");
    }
    
    private void buscarLibros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        String criterio = request.getParameter("criterio");
        List<Libro> libros = libroDAO.buscar(criterio);
        request.setAttribute("libros", libros);
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
        if (exito) request.getSession().setAttribute("mensaje", "Préstamo registrado exitosamente");
        else request.getSession().setAttribute("error", "Error al registrar préstamo");
        response.sendRedirect("ControladorSistema?action=listarPrestamos");
    }
    
    private void registrarDevolucion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        int idPrestamo = Integer.parseInt(request.getParameter("id"));
        boolean exito = prestamoDAO.registrarDevolucion(idPrestamo);
        if (exito) request.getSession().setAttribute("mensaje", "Devolución registrada exitosamente");
        response.sendRedirect("ControladorSistema?action=listarPrestamos");
    }
    
    private void mostrarFormularioEditarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;

        int idPrestamo = Integer.parseInt(request.getParameter("id"));
        Prestamo prestamo = prestamoDAO.obtenerPorId(idPrestamo);

        if (prestamo != null) {
            List<Libro> libros = libroDAO.listarTodos();
            List<Lector> lectores = lectorDAO.listarTodos();
            List<Usuario> usuarios = usuarioDAO.listarTodos(); // ✅ AGREGADO

            request.setAttribute("prestamo", prestamo);
            request.setAttribute("libros", libros);
            request.setAttribute("lectores", lectores);

            // ✅ Guardar usuarios en sesión para que estén disponibles en el JSP
            request.getSession().setAttribute("usuarios", usuarios);

            request.getRequestDispatcher("vistas/form_prestamo_edit.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("error", "Préstamo no encontrado");
            response.sendRedirect("ControladorSistema?action=listarPrestamos");
        }
    }
    
    private void actualizarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        try {
            // ✅ Obtener parámetros con validación
            int idPrestamo = Integer.parseInt(request.getParameter("id"));

            String libroParam = request.getParameter("libro");
            String lectorParam = request.getParameter("lector");
            String usuarioParam = request.getParameter("usuario");

            // Validar que los parámetros no estén vacíos
            if (libroParam == null || libroParam.isEmpty() ||
                lectorParam == null || lectorParam.isEmpty() ||
                usuarioParam == null || usuarioParam.isEmpty()) {
                request.getSession().setAttribute("error", "Error: Faltan datos obligatorios");
                response.sendRedirect("ControladorSistema?action=listarPrestamos");
                return;
            }

            int idLibro = Integer.parseInt(libroParam);
            int idLector = Integer.parseInt(lectorParam);
            int idUsuario = Integer.parseInt(usuarioParam);

            String fechaPrestamoStr = request.getParameter("fechaPrestamo");
            String fechaDevolucionEsperadaStr = request.getParameter("fechaDevolucionEsperada");
            String fechaDevolucionRealStr = request.getParameter("fechaDevolucionReal");
            String estado = request.getParameter("estado");
            String observaciones = request.getParameter("observaciones");
            String fechaRegistroStr = request.getParameter("fechaRegistro");

            // Crear objeto Prestamo
            Prestamo prestamo = new Prestamo();
            prestamo.setIdPrestamo(idPrestamo);
            prestamo.setIdLibro(idLibro);
            prestamo.setIdLector(idLector);
            prestamo.setIdUsuario(idUsuario);
            prestamo.setFechaPrestamo(java.sql.Date.valueOf(fechaPrestamoStr));
            prestamo.setFechaDevolucionEsperada(java.sql.Date.valueOf(fechaDevolucionEsperadaStr));

            // ✅ Manejar fecha de devolución real (puede ser null)
            if (fechaDevolucionRealStr != null && !fechaDevolucionRealStr.isEmpty()) {
                prestamo.setFechaDevolucionReal(java.sql.Date.valueOf(fechaDevolucionRealStr));
            } else {
                prestamo.setFechaDevolucionReal(null);
            }

            prestamo.setEstado(estado);
            prestamo.setObservaciones(observaciones);

            // ✅ Preservar fecha_registro (solo en edición)
            if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
                String fechaRegistroSQL = fechaRegistroStr.replace("T", " ");
                prestamo.setFechaRegistro(java.sql.Timestamp.valueOf(fechaRegistroSQL + ":00"));
            }

            boolean exito = prestamoDAO.actualizar(prestamo);

            if (exito) {
                request.getSession().setAttribute("mensaje", "Préstamo actualizado exitosamente");
            } else {
                request.getSession().setAttribute("error", "Error al actualizar préstamo");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Error: Formato de número inválido - " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", "Error: Fecha inválida. Usa formato YYYY-MM-DD");
            e.printStackTrace();
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("ControladorSistema?action=listarPrestamos");
    }
    
    private void cancelarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        try {
            int idPrestamo = Integer.parseInt(request.getParameter("id"));
            boolean exito = prestamoDAO.cancelarPrestamo(idPrestamo);
            if (exito) request.getSession().setAttribute("mensaje", "Préstamo cancelado exitosamente");
            else request.getSession().setAttribute("error", "Error al cancelar préstamo");
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Error al cancelar: " + e.getMessage());
        }
        response.sendRedirect("ControladorSistema?action=listarPrestamos");
    }
    
    private void eliminarPrestamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        try {
            int idPrestamo = Integer.parseInt(request.getParameter("id"));
            boolean exito = prestamoDAO.eliminar(idPrestamo);
            if (exito) request.getSession().setAttribute("mensaje", "Préstamo eliminado exitosamente");
            else request.getSession().setAttribute("error", "Error al eliminar préstamo");
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        response.sendRedirect("ControladorSistema?action=listarPrestamos");
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
        List<Map<String, Object>> reservas = reservaDAO.listarTodas();
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
        if (exito) request.getSession().setAttribute("mensaje", "Reserva creada exitosamente");
        else request.getSession().setAttribute("error", "Error al crear reserva");
        response.sendRedirect("ControladorSistema?action=listarReservas");
    }
    
    private void cancelarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        try {
            int idReserva = Integer.parseInt(request.getParameter("id"));
            boolean exito = reservaDAO.cancelarReserva(idReserva);
            if (exito) request.getSession().setAttribute("mensaje", "Reserva cancelada exitosamente");
            else request.getSession().setAttribute("error", "Error al cancelar reserva");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        response.sendRedirect("ControladorSistema?action=listarReservas");
    }
    
    private void completarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        try {
            int idReserva = Integer.parseInt(request.getParameter("id"));
            boolean exito = reservaDAO.completarReserva(idReserva);
            if (exito) request.getSession().setAttribute("mensaje", "Reserva completada exitosamente");
            else request.getSession().setAttribute("error", "Error al completar reserva");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error: " + e.getMessage());
        }
        response.sendRedirect("ControladorSistema?action=listarReservas");
    }
    
    private void mostrarFormularioEditarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        int idReserva = Integer.parseInt(request.getParameter("id"));
        Reserva reserva = reservaDAO.obtenerPorId(idReserva);
        if (reserva != null) {
            List<Libro> libros = libroDAO.listarTodos();
            List<Lector> lectores = lectorDAO.listarTodos();
            request.setAttribute("reserva", reserva);
            request.setAttribute("libros", libros);
            request.setAttribute("lectores", lectores);
            request.getRequestDispatcher("vistas/form_reserva_edit.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("error", "Reserva no encontrada");
            response.sendRedirect("ControladorSistema?action=listarReservas");
        }
    }
    
    private void actualizarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;

        try {
            // ============================================
            // CAPTURAR TODOS LOS PARÁMETROS
            // ============================================

            // ID de la reserva
            int idReserva = Integer.parseInt(request.getParameter("id"));

            // Relaciones (Foreign Keys)
            int idLibro = Integer.parseInt(request.getParameter("libro"));
            int idLector = Integer.parseInt(request.getParameter("lector"));

            // Fechas de negocio
            String fechaReservaStr = request.getParameter("fechaReserva");
            String fechaExpiracionStr = request.getParameter("fechaExpiracion");

            // Estado
            String estado = request.getParameter("estado");

            // Timestamps del sistema
            String fechaRegistroStr = request.getParameter("fechaRegistro");
            String fechaModificacionStr = request.getParameter("fechaModificacion");

            // ============================================
            // VALIDACIONES BÁSICAS
            // ============================================
            if (estado == null || estado.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Error: El estado es obligatorio");
                response.sendRedirect("ControladorSistema?action=listarReservas");
                return;
            }

            // ============================================
            // CREAR Y POBLAR OBJETO RESERVA
            // ============================================
            Reserva reserva = new Reserva();

            // Campos básicos
            reserva.setIdReserva(idReserva);
            reserva.setIdLibro(idLibro);
            reserva.setIdLector(idLector);
            reserva.setEstado(estado);

            // Fechas de negocio (date)
            reserva.setFechaReserva(java.sql.Date.valueOf(fechaReservaStr));
            reserva.setFechaExpiracion(java.sql.Date.valueOf(fechaExpiracionStr));

            // Timestamps del sistema (timestamp)
            // Formato de entrada: "2024-01-15T10:30" → Convertir a "2024-01-15 10:30:00"
            if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
                String fechaRegistroSQL = fechaRegistroStr.replace("T", " ") + ":00";
                reserva.setFechaRegistro(java.sql.Timestamp.valueOf(fechaRegistroSQL));
            } else {
                // Si no se proporciona, usar la fecha actual
                reserva.setFechaRegistro(new java.sql.Timestamp(System.currentTimeMillis()));
            }

            if (fechaModificacionStr != null && !fechaModificacionStr.isEmpty()) {
                String fechaModificacionSQL = fechaModificacionStr.replace("T", " ") + ":00";
                reserva.setFechaModificacion(java.sql.Timestamp.valueOf(fechaModificacionSQL));
            } else {
                // Si no se proporciona, usar la fecha actual
                reserva.setFechaModificacion(new java.sql.Timestamp(System.currentTimeMillis()));
            }

            // ============================================
            // EJECUTAR ACTUALIZACIÓN
            // ============================================
            boolean exito = reservaDAO.actualizar(reserva);

            if (exito) {
                request.getSession().setAttribute("mensaje", 
                    "Reserva #" + idReserva + " actualizada exitosamente");
            } else {
                request.getSession().setAttribute("error", 
                    "❌ Error al actualizar la reserva en la base de datos");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", 
                "Error: Formato de número inválido - " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", 
                "Error: Formato de fecha inválido. Usa formato YYYY-MM-DD o YYYY-MM-DDTHH:MM");
            e.printStackTrace();
        } catch (SQLException e) {
            request.getSession().setAttribute("error", 
                "Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            request.getSession().setAttribute("error", 
                "Error inesperado al actualizar: " + e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect("ControladorSistema?action=listarReservas");
    }
    
    private void eliminarReserva(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        try {
            int idReserva = Integer.parseInt(request.getParameter("id"));
            boolean exito = reservaDAO.eliminar(idReserva);
            if (exito) request.getSession().setAttribute("mensaje", "Reserva eliminada exitosamente");
            else request.getSession().setAttribute("error", "Error al eliminar reserva");
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        response.sendRedirect("ControladorSistema?action=listarReservas");
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
            request.setAttribute("bajas", bajas);
            request.getRequestDispatcher("vistas/historial_bajas.jsp").forward(request, response);
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error al obtener historial de bajas: " + e.getMessage());
            response.sendRedirect("ControladorSistema?action=dashboard");
        }
    }
    
    private void mostrarFormularioEditarBaja(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException, SQLException {
            if (!validarSesion(request, response)) return;
            if (!esAdministrador(request, response)) return;

            int idBaja = Integer.parseInt(request.getParameter("id"));
            BajaLibro baja = bajaLibroDAO.obtenerPorId(idBaja);

            if (baja != null) {
                // ✅ Cargar listas de Libros y Usuarios para los dropdowns
                List<Libro> libros = libroDAO.listarTodos();
                List<Usuario> usuarios = usuarioDAO.listarTodos();

                request.setAttribute("baja", baja);
                request.setAttribute("libros", libros);
                request.setAttribute("usuarios", usuarios);
                request.getRequestDispatcher("vistas/form_baja.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Registro de baja no encontrado");
                response.sendRedirect("ControladorSistema?action=verHistorialBajas");
            }
        }

        private void actualizarBaja(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException, SQLException {
            if (!validarSesion(request, response)) return;
            if (!esAdministrador(request, response)) return;

            try {
                int idBaja = Integer.parseInt(request.getParameter("id"));
                int idLibro = Integer.parseInt(request.getParameter("libro"));
                int idUsuario = Integer.parseInt(request.getParameter("usuario"));
                String motivo = request.getParameter("motivo");
                String descripcion = request.getParameter("descripcion");
                String fechaBajaStr = request.getParameter("fechaBaja");
                String fechaRegistroStr = request.getParameter("fechaRegistro");
                String fechaModificacionStr = request.getParameter("fechaModificacion");

                BajaLibro baja = new BajaLibro();
                baja.setIdBaja(idBaja);
                baja.setIdLibro(idLibro);
                baja.setIdUsuario(idUsuario);
                baja.setMotivo(motivo);
                baja.setDescripcion(descripcion);

                // Convertir fecha (date) a java.sql.Date
                baja.setFechaBaja(java.sql.Date.valueOf(fechaBajaStr));

                // Convertir datetime-local a java.sql.Timestamp
                if (fechaRegistroStr != null && !fechaRegistroStr.isEmpty()) {
                    // Formato: "2024-01-15T10:30" → "2024-01-15 10:30:00"
                    String fechaRegistroSQL = fechaRegistroStr.replace("T", " ");
                    baja.setFechaRegistro(java.sql.Timestamp.valueOf(fechaRegistroSQL + ":00"));
                }

                if (fechaModificacionStr != null && !fechaModificacionStr.isEmpty()) {
                    // Formato: "2024-01-15T10:30" → "2024-01-15 10:30:00"
                    String fechaModificacionSQL = fechaModificacionStr.replace("T", " ");
                    baja.setFechaModificacion(java.sql.Timestamp.valueOf(fechaModificacionSQL + ":00"));
                }

                boolean exito = bajaLibroDAO.actualizar(baja);

                if (exito) {
                    request.getSession().setAttribute("mensaje", "Registro de baja actualizado exitosamente");
                } else {
                    request.getSession().setAttribute("error", "Error al actualizar registro de baja");
                }
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "Error: ID inválido - " + e.getMessage());
            } catch (IllegalArgumentException e) {
                request.getSession().setAttribute("error", "Error: Fecha inválida. Usa formato correcto");
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Error al actualizar: " + e.getMessage());
                e.printStackTrace();
            }

            response.sendRedirect("ControladorSistema?action=verHistorialBajas");
        }
    
    private void eliminarBaja(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        if (!validarSesion(request, response)) return;
        if (!esAdministrador(request, response)) return;
        try {
            int idBaja = Integer.parseInt(request.getParameter("id"));
            boolean exito = bajaLibroDAO.eliminar(idBaja);
            if (exito) request.getSession().setAttribute("mensaje", "Registro de baja eliminado exitosamente");
            else request.getSession().setAttribute("error", "Error al eliminar registro de baja");
        } catch (SQLException e) {
            request.getSession().setAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        response.sendRedirect("ControladorSistema?action=verHistorialBajas");
    }
       
    // ============================================
    // MÉTODOS DE UTILIDAD
    // ============================================
    private boolean validarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("ControladorSistema?action=login");
            return false;
        }
        return true;
    }
    
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