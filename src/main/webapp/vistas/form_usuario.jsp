<%-- 
    Document   : form_usuario
    Created on : 09/01/2026, 15:40:13
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${modo == 'nuevo' ? 'Nuevo' : 'Editar'} Usuario - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>${modo == 'nuevo' ? 'Nuevo' : 'Editar'} Usuario</h1>
                <p class="breadcrumb">Dashboard / Usuarios / ${modo == 'nuevo' ? 'Nuevo' : 'Editar'}</p>
            </div>
            
            <div class="table-container">
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="guardarUsuario">
                    <input type="hidden" name="modo" value="${modo}">
                    <c:if test="${modo == 'editar'}">
                        <input type="hidden" name="id" value="${usuario.idUsuario}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="nombre">Nombre: *</label>
                        <input type="text" id="nombre" name="nombre" 
                               value="${usuario.nombre}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="apellido">Apellido: *</label>
                        <input type="text" id="apellido" name="apellido" 
                               value="${usuario.apellido}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email: *</label>
                        <input type="email" id="email" name="email" 
                               value="${usuario.email}" required>
                    </div>
                    
                    <c:if test="${modo == 'nuevo'}">
                        <div class="form-group">
                            <label for="password">Contraseña: *</label>
                            <input type="password" id="password" name="password" 
                                   minlength="6" required>
                        </div>
                    </c:if>
                    
                    <div class="form-group">
                        <label for="rol">Rol: *</label>
                        <select id="rol" name="rol" required>
                            <option value="">Seleccione un rol</option>
                            <option value="ADMINISTRADOR" ${usuario.rol == 'ADMINISTRADOR' ? 'selected' : ''}>
                                Administrador
                            </option>
                            <option value="EMPLEADO" ${usuario.rol == 'EMPLEADO' ? 'selected' : ''}>
                                Empleado
                            </option>
                        </select>
                    </div>
                    
                    <c:if test="${modo == 'editar'}">
                        <div class="form-group">
                            <label for="estado">Estado: *</label>
                            <select id="estado" name="estado" required>
                                <option value="ACTIVO" ${usuario.estado == 'ACTIVO' ? 'selected' : ''}>
                                    Activo
                                </option>
                                <option value="INACTIVO" ${usuario.estado == 'INACTIVO' ? 'selected' : ''}>
                                    Inactivo
                                </option>
                            </select>
                        </div>
                    </c:if>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ✅ Guardar Usuario
                        </button>
                        <a href="ControladorSistema?action=listarUsuarios" class="btn btn-secondary">
                            ❌ Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>