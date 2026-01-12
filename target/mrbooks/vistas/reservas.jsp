<%-- 
    Document   : reservas
    Created on : 09/01/2026, 15:44:04
    Author     : ASUS
--%>
<%-- 
    Document   : reservas
    Created on : 09/01/2026, 15:44:04
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reservas - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Gestión de Reservas</h1>
                <p class="breadcrumb">Dashboard / Reservas</p>
            </div>
            
            <c:if test="${not empty sessionScope.mensaje}">
                <div class="alert alert-success">
                    <span>✅</span> ${sessionScope.mensaje}
                </div>
                <c:remove var="mensaje" scope="session"/>
            </c:if>
            
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-error">
                    <span>⚠️</span> ${sessionScope.error}
                </div>
                <c:remove var="error" scope="session"/>
            </c:if>
            
            <div class="actions-bar">
                <a href="ControladorSistema?action=nuevaReserva" class="btn btn-primary">
                    ➕ Nueva Reserva
                </a>
            </div>
            
            <div class="table-container">
                <h2>Reservas Activas</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Libro</th>
                            <th>ISBN</th>
                            <th>Lector</th>
                            <th>Cédula</th>
                            <th>Teléfono</th>
                            <th>Fecha Reserva</th>
                            <th>Fecha Expiración</th>
                            <th>Días Restantes</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="reserva" items="${reservas}">
                            <tr>
                                <td>${reserva.id_reserva}</td>
                                <td><strong>${reserva.libro}</strong></td>
                                <td>${reserva.isbn}</td>
                                <td>${reserva.lector}</td>
                                <td>${reserva.cedula}</td>
                                <td>${reserva.telefono}</td>
                                <td>${reserva.fecha_reserva}</td>
                                <td>${reserva.fecha_expiracion}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${reserva.dias_restantes >= 1}">
                                            <span class="badge badge-success">${reserva.dias_restantes} días</span>
                                        </c:when>
                                        <c:when test="${reserva.dias_restantes == 0}">
                                            <span class="badge badge-warning">Hoy</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-danger">Expirada</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <span class="badge badge-primary">${reserva.estado}</span>
                                </td>
                                <td class="actions">
                                    <a href="ControladorSistema?action=completarReserva&id=${reserva.id_reserva}" 
                                       class="btn-icon btn-success" title="Completar Reserva"
                                       onclick="return confirm('¿Marcar reserva como completada?')">✅</a>
                                    <a href="ControladorSistema?action=editarReserva&id=${reserva.id_reserva}" 
                                       class="btn-icon btn-edit" title="Editar">✏️</a>
                                    <a href="ControladorSistema?action=cancelarReserva&id=${reserva.id_reserva}" 
                                       class="btn-icon btn-warning" title="Cancelar Reserva"
                                       onclick="return confirm('⚠️ ¿Cancelar esta reserva?')">❌</a>
                                    <a href="ControladorSistema?action=eliminarReserva&id=${reserva.id_reserva}" 
                                       class="btn-icon btn-delete" title="Eliminar"
                                       onclick="return confirm('⚠️ ¿Estás seguro de ELIMINAR esta reserva?\n\nEsta acción NO se puede deshacer.\n\nLibro: ${reserva.libro}\nLector: ${reserva.lector}')">🗑️</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <c:if test="${empty reservas}">
                    <div class="empty-state">
                        <p>😕 No hay reservas activas</p>
                    </div>
                </c:if>
            </div>
        </main>
    </div>
</body>
</html>