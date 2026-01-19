<%-- 
    Document   : form_prestamo_edit
    ✅ ACTUALIZADO: Permite editar LITERALMENTE TODO (id_usuario, estado, fecha_devolucion_real, etc.)
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
                                <label>Num Préstamo</label>
                                <input type="text" value="${prestamo.idPrestamo}" readonly class="readonly-input">
                            </div>
                        </div>
                        
                        <!-- Seleccionar Libro -->
                        <div class="form-group">
                            <label for="libro">Libro <span class="required">*</span></label>
                            <select id="libro" name="libro" required>
                                <option value="">Seleccione un libro</option>
                                <c:forEach var="lib" items="${libros}">
                                    <option value="${lib.idLibro}" ${lib.idLibro == prestamo.idLibro ? 'selected' : ''}>
                                        ${lib.titulo} - ${lib.autor}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <!-- Seleccionar Lector -->
                        <div class="form-group">
                            <label for="lector">Lector <span class="required">*</span></label>
                            <select id="lector" name="lector" required>
                                <option value="">Seleccione un lector</option>
                                <c:forEach var="lec" items="${lectores}">
                                    <option value="${lec.idLector}" ${lec.idLector == prestamo.idLector ? 'selected' : ''}>
                                        ${lec.nombreCompleto} - ${lec.cedula}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <!-- Fechas -->
                        <div class="form-row">
                            <div class="form-group">
                                <label for="fechaPrestamo">Fecha de Préstamo <span class="required">*</span></label>
                                <input type="date" id="fechaPrestamo" name="fechaPrestamo" 
                                       value="${prestamo.fechaPrestamo}" required>
                            </div>
                            <div class="form-group">
                                <label for="fechaDevolucionEsperada">Fecha Devolución Esperada <span class="required">*</span></label>
                                <input type="date" id="fechaDevolucionEsperada" name="fechaDevolucionEsperada" 
                                       value="${prestamo.fechaDevolucionEsperada}" required>
                            </div>
                        </div>
                        
                        <!-- ✅ Fecha Devolución Real (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaDevolucionReal">Fecha Devolución Real</label>
                            <input type="date" id="fechaDevolucionReal" name="fechaDevolucionReal" 
                                   value="${prestamo.fechaDevolucionReal}">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - fecha real en que se devolvió el libro (opcional)
                            </small>
                        </div>
                    </div>
                    
                    <!-- Estado y Observaciones -->
                    <div class="form-section">
                        <h3>📋 Estado y Observaciones</h3>
                        
                        <!-- ✅ Estado (EDITABLE) -->
                        <div class="form-group">
                            <label for="estado">Estado <span class="required">*</span></label>
                            <select id="estado" name="estado" required>
                                <option value="ACTIVO" ${prestamo.estado == 'ACTIVO' ? 'selected' : ''}>ACTIVO</option>
                                <option value="DEVUELTO" ${prestamo.estado == 'DEVUELTO' ? 'selected' : ''}>DEVUELTO</option>
                                <option value="CANCELADO" ${prestamo.estado == 'CANCELADO' ? 'selected' : ''}>CANCELADO</option>
                                <option value="ATRASADO" ${prestamo.estado == 'ATRASADO' ? 'selected' : ''}>ATRASADO</option>
                            </select>
                        </div>
                        
                        <!-- Observaciones -->
                        <div class="form-group">
                            <label for="observaciones">Observaciones</label>
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