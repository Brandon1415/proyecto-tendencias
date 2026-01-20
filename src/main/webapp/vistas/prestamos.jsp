<%-- 
    Document   : prestamos
    ✅ ACTUALIZADO: Muestra TODOS los datos incluyendo observaciones y fecha_modificacion
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
                            <th>ISBN</th>
                            <th>Lector</th>
                            <th>Cédula</th>
                            <th>Empleado</th>
                            <th>Fecha Préstamo</th>
                            <th>Devolución Esperada</th>
                            <th>Devolución Real</th>
                            <th>Observaciones</th>
                            <th>Fecha Modificación</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="prestamo" items="${prestamos}">
                            <tr>
                                <td>${prestamo.id_prestamo}</td>
                                <td><strong>${prestamo.libro}</strong></td>
                                <td>${prestamo.isbn}</td>
                                <td>${prestamo.lector}</td>
                                <td>${prestamo.cedula}</td>
                                <td>${prestamo.empleado}</td>
                                <td>${prestamo.fecha_prestamo}</td>
                                <td>${prestamo.fecha_devolucion_esperada}</td>
                                <td>${prestamo.fecha_devolucion_real}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty prestamo.observaciones}">
                                            ${prestamo.observaciones}
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #7f8c8d; font-style: italic;">Sin observaciones</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${prestamo.fecha_modificacion}</td>
                                <td>
                                    <span class="badge ${prestamo.estado == 'DEVUELTO' ? 'badge-success' : 
                                                         prestamo.estado == 'ACTIVO' ? 'badge-warning' : 
                                                         prestamo.estado == 'CANCELADO' ? 'badge-secondary' : 'badge-danger'}">
                                        ${prestamo.estado}
                                    </span>
                                </td>
                                <td class="actions">
                                    <!-- Botón Editar -->
                                    <a href="ControladorSistema?action=editarPrestamo&id=${prestamo.id_prestamo}" 
                                       class="btn-icon btn-edit" title="Editar">✏️</a>
                                    
                                    <c:choose>
                                        <c:when test="${prestamo.estado == 'ACTIVO' || prestamo.estado == 'ATRASADO'}">
                                            <!-- Préstamos ACTIVOS o ATRASADOS -->
                                            <a href="ControladorSistema?action=registrarDevolucion&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-success" title="Registrar Devolución"
                                               onclick="return confirm('¿Confirmar devolución del libro?')">✅</a>
                                            <a href="ControladorSistema?action=cancelarPrestamo&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-warning" title="Cancelar Préstamo"
                                               onclick="return confirm('⚠️ ¿Cancelar este préstamo?')">❌</a>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- Préstamos DEVUELTOS o CANCELADOS -->
                                            <a href="ControladorSistema?action=eliminarPrestamo&id=${prestamo.id_prestamo}" 
                                               class="btn-icon btn-delete" title="Eliminar"
                                               onclick="return confirm('⚠️ ¿ELIMINAR este préstamo?\n\nEsta acción NO se puede deshacer.\n\nLibro: ${prestamo.libro}\nLector: ${prestamo.lector}')">🗑️</a>
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