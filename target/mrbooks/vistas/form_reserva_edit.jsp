<%-- 
    Document   : form_reserva_edit
    Created on : 11/01/2026, 21:46:27
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Reserva - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Editar Reserva</h1>
                <p class="breadcrumb">Dashboard / Reservas / Editar</p>
            </div>
            
            <div class="form-container">
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="actualizarReserva">
                    <input type="hidden" name="id" value="${reserva.idReserva}">
                    
                    <!-- Información de la Reserva -->
                    <div class="form-section">
                        <h3>📚 Información de la Reserva</h3>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label>ID Reserva:</label>
                                <input type="text" value="${reserva.idReserva}" readonly class="readonly-input">
                            </div>
                            <div class="form-group">
                                <label>Estado:</label>
                                <input type="text" value="${reserva.estado}" readonly class="readonly-input">
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="libro">Libro: *</label>
                                <select id="libro" name="libro" required>
                                    <option value="">Seleccione un libro</option>
                                    <c:forEach var="lib" items="${libros}">
                                        <option value="${lib.idLibro}" ${lib.idLibro == reserva.idLibro ? 'selected' : ''}>
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
                                        <option value="${lec.idLector}" ${lec.idLector == reserva.idLector ? 'selected' : ''}>
                                            ${lec.nombreCompleto} - ${lec.cedula}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label>Fecha de Reserva:</label>
                                <input type="date" value="${reserva.fechaReserva}" readonly class="readonly-input">
                            </div>
                            <div class="form-group">
                                <label for="fechaExpiracion">Fecha de Expiración: *</label>
                                <input type="date" id="fechaExpiracion" name="fechaExpiracion" 
                                       value="${reserva.fechaExpiracion}" required>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">💾 Guardar Cambios</button>
                        <a href="ControladorSistema?action=listarReservas" class="btn btn-secondary">❌ Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>