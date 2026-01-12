<%-- 
    Document   : prestamos
    Created on : 09/01/2026, 13:06:32
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Préstamos - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Gestión de Préstamos</h1>
                <p class="breadcrumb">Dashboard / Préstamos</p>
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
                <a href="ControladorSistema?action=nuevoPrestamo" class="btn btn-primary">
                    ➕ Nuevo Préstamo
                </a>
            </div>
            
            <div class="table-container">
                <h2>Historial de Préstamos</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Libro</th>
                            <th>Lector</th>
                            <th>Cédula</th>
                            <th>Fecha Préstamo</th>
                            <th>Fecha Devolución</th>
                            <th>Estado</th>
                            <th>Retraso</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="prestamo" items="${prestamos}">
                            <tr>
                                <td>${prestamo.id_prestamo}</td>
                                <td><strong>${prestamo.libro}</strong></td>
                                <td>${prestamo.lector}</td>
                                <td>${prestamo.cedula}</td>
                                <td>${prestamo.fecha_prestamo}</td>
                                <td>${prestamo.fecha_devolucion_esperada}</td>
                                <td>
                                    <span class="badge ${prestamo.estado == 'DEVUELTO' ? 'badge-success' : 
                                                         prestamo.estado == 'ACTIVO' ? 'badge-warning' : 
                                                         prestamo.estado == 'CANCELADO' ? 'badge-secondary' : 'badge-danger'}">
                                        ${prestamo.estado}
                                    </span>
                                </td>
                                <td>
                                    <c:if test="${prestamo.dias_retraso > 0}">
                                        <span class="badge badge-danger">${prestamo.dias_retraso} días</span>
                                    </c:if>
                                </td>
                                <td class="actions">
                                    <c:choose>
                                        <c:when test="${prestamo.estado == 'ACTIVO'}">
                                            <!-- Préstamos ACTIVOS: Devolver, Editar, Cancelar -->
                                            <a href="ControladorSistema?action=registrarDevolucion&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-success" title="Registrar Devolución"
                                               onclick="return confirm('¿Confirmar devolución del libro?')">✅</a>
                                            <a href="ControladorSistema?action=editarPrestamo&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-edit" title="Editar">✏️</a>
                                            <a href="ControladorSistema?action=cancelarPrestamo&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-warning" title="Cancelar Préstamo"
                                               onclick="return confirm('⚠️ ¿Cancelar este préstamo?\n\nEsto marcará el préstamo como cancelado.')">❌</a>
                                        </c:when>
                                        <c:when test="${prestamo.estado == 'ATRASADO'}">
                                            <!-- Préstamos ATRASADOS: Devolver, Editar, Cancelar -->
                                            <a href="ControladorSistema?action=registrarDevolucion&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-success" title="Registrar Devolución"
                                               onclick="return confirm('¿Confirmar devolución del libro?\n\nNota: Este préstamo está ATRASADO.')">✅</a>
                                            <a href="ControladorSistema?action=editarPrestamo&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-edit" title="Editar">✏️</a>
                                            <a href="ControladorSistema?action=cancelarPrestamo&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-warning" title="Cancelar Préstamo"
                                               onclick="return confirm('⚠️ ¿Cancelar este préstamo atrasado?')">❌</a>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Préstamos DEVUELTOS o CANCELADOS: Solo eliminar -->
                                            <a href="ControladorSistema?action=eliminarPrestamo&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-delete" title="Eliminar"
                                               onclick="return confirm('⚠️ ¿Estás seguro de ELIMINAR este préstamo?\n\nEsta acción NO se puede deshacer.\n\nLibro: ${prestamo.libro}\nLector: ${prestamo.lector}')">🗑️</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <c:if test="${empty prestamos}">
                    <div class="empty-state">
                        <p>😕 No se encontraron préstamos</p>
                    </div>
                </c:if>
            </div>
        </main>
    </div>
</body>
</html>