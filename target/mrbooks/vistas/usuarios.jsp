<%-- 
    Document   : usuarios
    ✅ VERSIÓN FINAL: Solo muestra campos que existen en la BD
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
                            <th>Apellido</th>
                            <th>Email</th>
                            <th>Rol</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr>
                                <td>${usuario.idUsuario}</td>
                                <td><strong>${usuario.nombre}</strong></td>
                                <td>${usuario.apellido}</td>
                                <td>${usuario.email}</td>
                                <td>
                                    <span class="badge ${usuario.rol == 'ADMINISTRADOR' ? 'badge-primary' : 'badge-secondary'}">
                                        ${usuario.rol}
                                    </span>
                                </td>
                                <td>
                                    <span class="badge ${usuario.estado == 'ACTIVO' ? 'badge-success' : 
                                                         usuario.estado == 'BLOQUEADO' ? 'badge-danger' : 'badge-warning'}">
                                        ${usuario.estado}
                                    </span>
                                </td>
                                <td class="actions">
                                    <a href="ControladorSistema?action=editarUsuario&id=${usuario.idUsuario}" 
                                       class="btn-icon btn-edit" title="Editar">✏️</a>
                                    
                                    <c:if test="${usuario.estado == 'BLOQUEADO'}">
                                        <a href="ControladorSistema?action=desbloquearUsuario&id=${usuario.idUsuario}" 
                                           class="btn-icon btn-success" title="Desbloquear"
                                           onclick="return confirm('¿Desbloquear usuario?\n\nNombre: ${usuario.nombre} ${usuario.apellido}')">🔓</a>
                                    </c:if>
                                    
                                    <a href="ControladorSistema?action=eliminarUsuario&id=${usuario.idUsuario}" 
                                       class="btn-icon btn-delete" title="Eliminar"
                                       onclick="return confirm('⚠️ ¿ELIMINAR este usuario?\n\nEsta acción NO se puede deshacer.\n\nUsuario: ${usuario.nombre} ${usuario.apellido}\nEmail: ${usuario.email}')">🗑️</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <!-- Sección de usuarios bloqueados -->
            <c:if test="${not empty bloqueados}">
                <div class="table-container">
                    <h2>⚠️ Usuarios Bloqueados</h2>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nombre</th>
                                <th>Email</th>
                                <th>Rol</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usuario" items="${bloqueados}">
                                <tr>
                                    <td>${usuario.idUsuario}</td>
                                    <td><strong>${usuario.nombre} ${usuario.apellido}</strong></td>
                                    <td>${usuario.email}</td>
                                    <td>
                                        <span class="badge ${usuario.rol == 'ADMINISTRADOR' ? 'badge-primary' : 'badge-secondary'}">
                                            ${usuario.rol}
                                        </span>
                                    </td>
                                    <td class="actions">
                                        <a href="ControladorSistema?action=desbloquearUsuario&id=${usuario.idUsuario}" 
                                           class="btn-icon btn-success" title="Desbloquear"
                                           onclick="return confirm('¿Desbloquear usuario?')">🔓</a>
                                        <a href="ControladorSistema?action=editarUsuario&id=${usuario.idUsuario}" 
                                           class="btn-icon btn-edit" title="Editar">✏️</a>
                                        <a href="ControladorSistema?action=eliminarUsuario&id=${usuario.idUsuario}" 
                                           class="btn-icon btn-delete" title="Eliminar"
                                           onclick="return confirm('⚠️ ¿ELIMINAR este usuario bloqueado?')">🗑️</a>
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