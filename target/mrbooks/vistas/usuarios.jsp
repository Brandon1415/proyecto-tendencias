<%-- 
    Document   : usuarios
    Created on : 09/01/2026, 13:03:48
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuarios - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Gestión de Usuarios</h1>
                <p class="breadcrumb">Dashboard / Usuarios</p>
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
                <a href="ControladorSistema?action=nuevoUsuario" class="btn btn-primary">
                    ➕ Nuevo Usuario
                </a>
            </div>
            
            <div class="table-container">
                <h2>Lista de Usuarios</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Rol</th>
                            <th>Estado</th>
                            <th>Bloqueado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr>
                                <td>${usuario.idUsuario}</td>
                                <td><strong>${usuario.nombreCompleto}</strong></td>
                                <td>${usuario.email}</td>
                                <td>
                                    <span class="badge ${usuario.rol == 'ADMINISTRADOR' ? 'badge-primary' : 'badge-secondary'}">
                                        ${usuario.rol}
                                    </span>
                                </td>
                                <td>
                                    <span class="badge ${usuario.estado == 'ACTIVO' ? 'badge-success' : 'badge-danger'}">
                                        ${usuario.estado}
                                    </span>
                                </td>
                                <td>
                                    <span class="badge ${usuario.bloqueado ? 'badge-warning' : 'badge-success'}">
                                        ${usuario.bloqueado ? 'SÍ' : 'NO'}
                                    </span>
                                </td>
                                <td class="actions">
                                    <a href="ControladorSistema?action=editarUsuario&id=${usuario.idUsuario}" 
                                       class="btn-icon btn-edit" title="Editar">✏️</a>
                                    <c:if test="${usuario.bloqueado}">
                                        <a href="ControladorSistema?action=desbloquearUsuario&id=${usuario.idUsuario}" 
                                           class="btn-icon btn-success" title="Desbloquear"
                                           onclick="return confirm('¿Desbloquear usuario?')">🔓</a>
                                    </c:if>
                                    <a href="ControladorSistema?action=eliminarUsuario&id=${usuario.idUsuario}" 
                                       class="btn-icon btn-delete" title="Eliminar"
                                       onclick="return confirm('⚠️ ¿Estás seguro de ELIMINAR este usuario?\n\nEsta acción NO se puede deshacer.\n\nUsuario: ${usuario.nombreCompleto}\nEmail: ${usuario.email}')">🗑️</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <c:if test="${not empty bloqueados}">
                <div class="table-container">
                    <h2>⚠️ Usuarios Bloqueados</h2>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Email</th>
                                <th>Rol</th>
                                <th>Intentos</th>
                                <th>Fecha Bloqueo</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usuario" items="${bloqueados}">
                                <tr>
                                    <td>${usuario.idUsuario}</td>
                                    <td>${usuario.email}</td>
                                    <td>${usuario.rol}</td>
                                    <td>${usuario.intentosFallidos}</td>
                                    <td>${usuario.fechaBloqueo}</td>
                                    <td class="actions">
                                        <a href="ControladorSistema?action=desbloquearUsuario&id=${usuario.idUsuario}" 
                                           class="btn btn-secondary btn-sm"
                                           onclick="return confirm('¿Desbloquear usuario?')">
                                            🔓 Desbloquear
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </main>
    </div>
</body>
</html>