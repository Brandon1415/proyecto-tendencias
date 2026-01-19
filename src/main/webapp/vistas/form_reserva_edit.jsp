<%-- 
    Formulario para editar una reserva existente
    Permite editar LITERALMENTE TODO:
    - Libro
    - Lector
    - Fecha de Reserva
    - Fecha de Expiración
    - Estado
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
                <!-- Información de la reserva actual -->
                <div class="info-box">
                    <h3>📋 Información de la Reserva</h3>
                    <p><strong>Num Reserva:</strong> ${reserva.idReserva}</p>
                    <p><strong>Fecha Modificación:</strong> ${reserva.fechaModificacion}</p>
                </div>
                
                <!-- Formulario de edición -->
                <form action="ControladorSistema" method="POST">
                    <input type="hidden" name="action" value="actualizarReserva">
                    <input type="hidden" name="id" value="${reserva.idReserva}">
                    
                    <!-- Estado -->
                    <div class="form-group">
                        <label for="estado">
                            Estado <span class="required">*</span>
                        </label>
                        <select id="estado" name="estado" required>
                            <option value="PENDIENTE" 
                                <c:if test="${reserva.estado == 'PENDIENTE'}">selected</c:if>>
                                PENDIENTE
                            </option>
                            <option value="COMPLETADO" 
                                <c:if test="${reserva.estado == 'COMPLETADO'}">selected</c:if>>
                                COMPLETADO
                            </option>
                            <option value="CANCELADO" 
                                <c:if test="${reserva.estado == 'CANCELADO'}">selected</c:if>>
                                CANCELADO
                            </option>
                        </select>
                    </div>
                    
                    <!-- Seleccionar Libro -->
                    <div class="form-group">
                        <label for="libro">
                            Libro <span class="required">*</span>
                        </label>
                        <select id="libro" name="libro" required>
                            <option value="">-- Selecciona un libro --</option>
                            <c:forEach var="libro" items="${libros}">
                                <option value="${libro.idLibro}" 
                                    <c:if test="${libro.idLibro == reserva.idLibro}">selected</c:if>>
                                    ${libro.titulo} (ISBN: ${libro.isbn})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- Seleccionar Lector -->
                    <div class="form-group">
                        <label for="lector">
                            Lector <span class="required">*</span>
                        </label>
                        <select id="lector" name="lector" required>
                            <option value="">-- Selecciona un lector --</option>
                            <c:forEach var="lector" items="${lectores}">
                                <option value="${lector.idLector}"
                                    <c:if test="${lector.idLector == reserva.idLector}">selected</c:if>>
                                    ${lector.nombre} ${lector.apellido} (${lector.cedula})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- Fecha de Reserva (AHORA EDITABLE) -->
                    <div class="form-group">
                        <label for="fechaReserva">
                            Fecha de Reserva <span class="required">*</span>
                        </label>
                        <input type="date" 
                               id="fechaReserva" 
                               name="fechaReserva" 
                               value="${reserva.fechaReserva}"
                               required>
                    </div>
                    
                    <!-- Fecha de Expiración -->
                    <div class="form-group">
                        <label for="fechaExpiracion">
                            Fecha de Expiración <span class="required">*</span>
                        </label>
                        <input type="date" 
                               id="fechaExpiracion" 
                               name="fechaExpiracion" 
                               value="${reserva.fechaExpiracion}"
                               required>
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