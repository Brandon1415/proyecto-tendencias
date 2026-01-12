<%-- 
    Document   : form_prestamo_edit
    Created on : 11/01/2026, 19:49:03
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <h1>Editar Préstamo</h1>
                <p class="breadcrumb">Dashboard / Préstamos / Editar</p>
            </div>
            
            <div class="form-container">
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="actualizarPrestamo">
                    <input type="hidden" name="id" value="${prestamo.idPrestamo}">
                    
                    <!-- Información del Préstamo -->
                    <div class="form-section">
                        <h3>📚 Información del Préstamo</h3>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label>Num Prestamo</label>
                                <input type="text" value="${prestamo.idPrestamo}" readonly class="readonly-input">
                            </div>
                            <div class="form-group">
                                <label>Estado:</label>
                                <input type="text" value="${prestamo.estado}" readonly class="readonly-input">
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="libro">Libro: *</label>
                                <select id="libro" name="libro" required>
                                    <option value="">Seleccione un libro</option>
                                    <c:forEach var="lib" items="${libros}">
                                        <option value="${lib.idLibro}" ${lib.idLibro == prestamo.idLibro ? 'selected' : ''}>
                                            ${lib.titulo} - ${lib.autor}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="lector">Lector: *</label>
                                <select id="lector" name="lector" required>
                                    <option value="">Seleccione un lector</option>
                                    <c:forEach var="lec" items="${lectores}">
                                        <option value="${lec.idLector}" ${lec.idLector == prestamo.idLector ? 'selected' : ''}>
                                            ${lec.nombreCompleto} - ${lec.cedula}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="fechaPrestamo">Fecha de Préstamo: *</label>
                                <input type="date" id="fechaPrestamo" name="fechaPrestamo" 
                                       value="${prestamo.fechaPrestamo}" required>
                            </div>
                            <div class="form-group">
                                <label for="fechaDevolucionEsperada">Fecha Devolución Esperada: *</label>
                                <input type="date" id="fechaDevolucionEsperada" name="fechaDevolucionEsperada" 
                                       value="${prestamo.fechaDevolucionEsperada}" required>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Observaciones -->
                    <div class="form-section">
                        <h3>📝 Observaciones</h3>
                        <div class="form-group">
                            <label for="observaciones">Observaciones:</label>
                            <textarea id="observaciones" name="observaciones" rows="4" 
                                      placeholder="Agregar observaciones sobre el préstamo...">${prestamo.observaciones}</textarea>
                        </div>
                    </div>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">💾 Guardar Cambios</button>
                        <a href="ControladorSistema?action=listarPrestamos" class="btn btn-secondary">❌ Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>