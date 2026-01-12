<%-- 
    Document   : lectores
    Created on : 09/01/2026, 13:04:41
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lectores - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Gestión de Lectores</h1>
                <p class="breadcrumb">Dashboard / Lectores</p>
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
                <a href="ControladorSistema?action=nuevoLector" class="btn btn-primary">
                    ➕ Nuevo Lector
                </a>
                
                <form action="ControladorSistema" method="get" class="search-form">
                    <input type="hidden" name="action" value="buscarLectores">
                    <input type="text" name="criterio" placeholder="Buscar..." value="${criterio}" class="search-input">
                    <button type="submit" class="btn btn-secondary">🔍 Buscar</button>
                </form>
            </div>
            
            <div class="table-container">
                <h2>Lista de Lectores</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nombre</th>
                            <th>Cédula</th>
                            <th>Correo</th>
                            <th>Teléfono</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="lector" items="${lectores}">
                            <tr>
                                <td>${lector.idLector}</td>
                                <td><strong>${lector.nombreCompleto}</strong></td>
                                <td>${lector.cedula}</td>
                                <td>${lector.correo}</td>
                                <td>${lector.telefono}</td>
                                <td>
                                    <span class="badge ${lector.estado == 'ACTIVO' ? 'badge-success' : 'badge-danger'}">
                                        ${lector.estado}
                                    </span>
                                </td>
                                <td class="actions">
                                    <a href="ControladorSistema?action=editarLector&id=${lector.idLector}" 
                                       class="btn-icon btn-edit" title="Editar">✏️</a>
                                    <a href="ControladorSistema?action=eliminarLector&id=${lector.idLector}" 
                                       class="btn-icon btn-delete" title="Eliminar"
                                       onclick="return confirm('⚠️ ¿Estás seguro de ELIMINAR este lector?\n\nEsta acción NO se puede deshacer.\n\nLector: ${lector.nombreCompleto}\nCédula: ${lector.cedula}')">🗑️</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <c:if test="${empty lectores}">
                    <div class="empty-state">
                        <p>😕 No se encontraron lectores</p>
                    </div>
                </c:if>
            </div>
        </main>
    </div>
</body>
</html>