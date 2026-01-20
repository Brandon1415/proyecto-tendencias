<%-- 
    Document   : form_prestamo_edit
    ✅ ACTUALIZADO: Permite editar LITERALMENTE TODO (id_usuario, estado, fecha_devolucion_real, etc.)
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Préstamo - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>✏️ Editar Préstamo #${prestamo.idPrestamo}</h1>
                <p class="breadcrumb">Dashboard / Préstamos / Editar</p>
            </div>
            
            <div class="table-container">
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="actualizarPrestamo">
                    <input type="hidden" name="id" value="${prestamo.idPrestamo}">
                    
                    <!-- ✅ CAMPO OCULTO: Preservar fecha_registro -->
                    <fmt:formatDate value="${prestamo.fechaRegistro}" pattern="yyyy-MM-dd'T'HH:mm" var="fechaRegistroFormateada"/>
                    <input type="hidden" name="fechaRegistro" value="${fechaRegistroFormateada}">
                    
                    <div class="form-row">
                        <!-- Seleccionar Libro -->
                        <div class="form-group">
                            <label for="libro">📚 Libro: <span class="required">*</span></label>
                            <select id="libro" name="libro" required>
                                <option value="">-- Selecciona un libro --</option>
                                <c:forEach var="libro" items="${libros}">
                                    <option value="${libro.idLibro}" 
                                        ${libro.idLibro == prestamo.idLibro ? 'selected' : ''}>
                                        ${libro.titulo} - ${libro.autor} (ISBN: ${libro.isbn})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <!-- Seleccionar Lector -->
                        <div class="form-group">
                            <label for="lector">👤 Lector: <span class="required">*</span></label>
                            <select id="lector" name="lector" required>
                                <option value="">-- Selecciona un lector --</option>
                                <c:forEach var="lector" items="${lectores}">
                                    <option value="${lector.idLector}"
                                        ${lector.idLector == prestamo.idLector ? 'selected' : ''}>
                                        ${lector.nombre} ${lector.apellido} - ${lector.cedula}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <!-- ✅ Usuario Responsable -->
                    <div class="form-group">
                        <label for="usuario">👨‍💼 Usuario Responsable: <span class="required">*</span></label>
                        <select id="usuario" name="usuario" required>
                            <option value="">-- Selecciona un usuario --</option>
                            <c:forEach var="usr" items="${sessionScope.usuarios}">
                                <option value="${usr.idUsuario}"
                                    ${usr.idUsuario == prestamo.idUsuario ? 'selected' : ''}>
                                    ${usr.nombre} ${usr.apellido} (${usr.rol})
                                </option>
                            </c:forEach>
                        </select>
                        <small style="color: #7f8c8d;">Si no aparecen opciones, carga los usuarios primero</small>
                    </div>
                    
                    <div class="form-row">
                        <!-- Fecha de Préstamo -->
                        <div class="form-group">
                            <label for="fechaPrestamo">📅 Fecha de Préstamo: <span class="required">*</span></label>
                            <input type="date" id="fechaPrestamo" name="fechaPrestamo" 
                                   value="${prestamo.fechaPrestamo}" required>
                        </div>
                        
                        <!-- Fecha de Devolución Esperada -->
                        <div class="form-group">
                            <label for="fechaDevolucionEsperada">⏰ Fecha de Devolución Esperada: <span class="required">*</span></label>
                            <input type="date" id="fechaDevolucionEsperada" name="fechaDevolucionEsperada" 
                                   value="${prestamo.fechaDevolucionEsperada}" required>
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <!-- Fecha de Devolución Real -->
                        <div class="form-group">
                            <label for="fechaDevolucionReal">✅ Fecha de Devolución Real:</label>
                            <input type="date" id="fechaDevolucionReal" name="fechaDevolucionReal" 
                                   value="${prestamo.fechaDevolucionReal}">
                            <small style="color: #7f8c8d;">Dejar vacío si aún no se ha devuelto</small>
                        </div>
                        
                        <!-- ✅ ESTADO - CAMPO CRÍTICO -->
                        <div class="form-group">
                            <label for="estado">🔖 Estado: <span class="required">*</span></label>
                            <select id="estado" name="estado" required>
                                <option value="ACTIVO" ${prestamo.estado == 'ACTIVO' ? 'selected' : ''}>
                                    🟢 ACTIVO
                                </option>
                                <option value="DEVUELTO" ${prestamo.estado == 'DEVUELTO' ? 'selected' : ''}>
                                    ✅ DEVUELTO
                                </option>
                                <option value="ATRASADO" ${prestamo.estado == 'ATRASADO' ? 'selected' : ''}>
                                    🔴 ATRASADO
                                </option>
                                <option value="CANCELADO" ${prestamo.estado == 'CANCELADO' ? 'selected' : ''}>
                                    ❌ CANCELADO
                                </option>
                            </select>
                        </div>
                    </div>
                    
                    <!-- Observaciones -->
                    <div class="form-group">
                        <label for="observaciones">📝 Observaciones:</label>
                        <textarea id="observaciones" name="observaciones" rows="4" 
                                  placeholder="Escribe cualquier observación sobre este préstamo...">${prestamo.observaciones}</textarea>
                    </div>
                    
                    <!-- Información de registro (solo lectura) -->
                    <div class="alert alert-info" style="background-color: #e3f2fd; border-left: 4px solid #2196F3; padding: 12px; margin: 20px 0;">
                        <strong>ℹ️ Información del Registro:</strong><br>
                        <small>
                            📌 ID Préstamo: <strong>#${prestamo.idPrestamo}</strong><br>
                            🕐 Registrado: <fmt:formatDate value="${prestamo.fechaRegistro}" pattern="dd/MM/yyyy HH:mm"/><br>
                            🔄 Última modificación: <fmt:formatDate value="${prestamo.fechaModificacion}" pattern="dd/MM/yyyy HH:mm"/>
                        </small>
                    </div>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ✅ Guardar Cambios
                        </button>
                        <a href="ControladorSistema?action=listarPrestamos" class="btn btn-secondary">
                            ❌ Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>
    
    <script>
        // ✅ Validación antes de enviar
        document.querySelector('form').addEventListener('submit', function(e) {
            const libro = document.getElementById('libro').value;
            const lector = document.getElementById('lector').value;
            const usuario = document.getElementById('usuario').value;
            const estado = document.getElementById('estado').value;
            
            if (!libro || !lector || !usuario || !estado) {
                e.preventDefault();
                alert('⚠️ Por favor completa todos los campos obligatorios');
                return false;
            }
        });
    </script>
</body>
</html>