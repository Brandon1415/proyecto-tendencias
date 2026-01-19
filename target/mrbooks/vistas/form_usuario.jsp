<%-- 
    Document   : form_usuario
    Formulario para crear y editar usuarios
    EDITA LITERALMENTE TODO: nombre, apellido, email, rol, estado, intentos, fechas
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
            
            <div class="form-container">
                <!-- Información del usuario (si es edición) -->
                <c:if test="${modo == 'editar'}">
                    <div class="info-box">
                        <h3>👤 Información del Usuario</h3>
                        <p><strong>ID Usuario:</strong> ${usuario.idUsuario}</p>
                    </div>
                </c:if>
                
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="guardarUsuario">
                    <input type="hidden" name="modo" value="${modo}">
                    <c:if test="${modo == 'editar'}">
                        <input type="hidden" name="id" value="${usuario.idUsuario}">
                    </c:if>
                    
                    <!-- Nombre -->
                    <div class="form-group">
                        <label for="nombre">Nombre <span class="required">*</span></label>
                        <input type="text" id="nombre" name="nombre" 
                               value="${usuario.nombre}" required maxlength="100">
                    </div>
                    
                    <!-- Apellido -->
                    <div class="form-group">
                        <label for="apellido">Apellido <span class="required">*</span></label>
                        <input type="text" id="apellido" name="apellido" 
                               value="${usuario.apellido}" required maxlength="100">
                    </div>
                    
                    <!-- Email -->
                    <div class="form-group">
                        <label for="email">Email <span class="required">*</span></label>
                        <input type="email" id="email" name="email" 
                               value="${usuario.email}" required maxlength="150">
                    </div>
                    
                    <!-- Contraseña (solo en nuevo) -->
                    <c:if test="${modo == 'nuevo'}">
                        <div class="form-group">
                            <label for="password">Contraseña <span class="required">*</span></label>
                            <input type="password" id="password" name="password" 
                                   minlength="6" required>
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Mínimo 6 caracteres
                            </small>
                        </div>
                    </c:if>
                    
                    <!-- Rol -->
                    <div class="form-group">
                        <label for="rol">Rol <span class="required">*</span></label>
                        <select id="rol" name="rol" required>
                            <option value="">-- Selecciona un rol --</option>
                            <option value="ADMINISTRADOR" ${usuario.rol == 'ADMINISTRADOR' ? 'selected' : ''}>
                                Administrador
                            </option>
                            <option value="EMPLEADO" ${usuario.rol == 'EMPLEADO' ? 'selected' : ''}>
                                Empleado
                            </option>
                        </select>
                    </div>
                    
                    <!-- Estado (solo en edición) -->
                    <c:if test="${modo == 'editar'}">
                        <div class="form-group">
                            <label for="estado">Estado <span class="required">*</span></label>
                            <select id="estado" name="estado" required>
                                <option value="ACTIVO" ${usuario.estado == 'ACTIVO' ? 'selected' : ''}>
                                    Activo
                                </option>
                                <option value="INACTIVO" ${usuario.estado == 'INACTIVO' ? 'selected' : ''}>
                                    Inactivo
                                </option>
                                <option value="BLOQUEADO" ${usuario.estado == 'BLOQUEADO' ? 'selected' : ''}>
                                    Bloqueado
                                </option>
                            </select>
                        </div>
                        
                        <!-- ✅ Intentos Fallidos (EDITABLE) -->
                        <div class="form-group">
                            <label for="intentos">Intentos Fallidos</label>
                            <input type="number" id="intentos" name="intentos"
                                   value="${usuario.intentosFallidos}" 
                                   min="0" max="10">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - controla cuántos intentos fallidos ha tenido
                            </small>
                        </div>
                        
                        <!-- ✅ Fecha Bloqueo (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaBloqueo">Fecha Bloqueo</label>
                            <input type="datetime-local" id="fechaBloqueo" name="fechaBloqueo"
                                   value="${usuario.fechaBloqueo}">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - fecha y hora en que se bloqueó
                            </small>
                        </div>
                        
                        <!-- ✅ Fecha Modificación (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaModificacion">Fecha Modificación</label>
                            <input type="datetime-local" id="fechaModificacion" name="fechaModificacion"
                                   value="${usuario.fechaModificacion}">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - última fecha de modificación
                            </small>
                        </div>
                    </c:if>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            💾 Guardar Usuario
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