<%-- 
    Document   : form_lector
    ✅ CORREGIDO: Preserva fecha_registro al editar
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${modo == 'nuevo' ? 'Nuevo' : 'Editar'} Lector - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>${modo == 'nuevo' ? 'Nuevo' : 'Editar'} Lector</h1>
                <p class="breadcrumb">Dashboard / Lectores / ${modo == 'nuevo' ? 'Nuevo' : 'Editar'}</p>
            </div>
            
            <div class="table-container">
                <!-- Información del lector (si es edición) -->
                <c:if test="${modo == 'editar'}">
                    <div class="info-box">
                        <h3>👥 Información del Lector</h3>
                        <p><strong>ID Lector:</strong> ${lector.idLector}</p>
                        <p><strong>Fecha Registro:</strong> ${lector.fechaRegistro}</p>
                    </div>
                </c:if>
                
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="guardarLector">
                    <input type="hidden" name="modo" value="${modo}">
                    <c:if test="${modo == 'editar'}">
                        <input type="hidden" name="id" value="${lector.idLector}">
                    </c:if>
                    
                    <!-- Nombre -->
                    <div class="form-group">
                        <label for="nombre">Nombre <span class="required">*</span></label>
                        <input type="text" id="nombre" name="nombre" 
                               value="${lector.nombre}" required maxlength="100">
                    </div>
                    
                    <!-- Apellido -->
                    <div class="form-group">
                        <label for="apellido">Apellido <span class="required">*</span></label>
                        <input type="text" id="apellido" name="apellido" 
                               value="${lector.apellido}" required maxlength="100">
                    </div>
                    
                    <!-- Cédula -->
                    <div class="form-group">
                        <label for="cedula">Cédula <span class="required">*</span></label>
                        <input type="text" id="cedula" name="cedula" 
                               value="${lector.cedula}" required maxlength="20">
                    </div>
                    
                    <!-- Correo Electrónico -->
                    <div class="form-group">
                        <label for="correo">Correo Electrónico <span class="required">*</span></label>
                        <input type="email" id="correo" name="correo" 
                               value="${lector.correo}" required maxlength="150">
                    </div>
                    
                    <!-- Teléfono -->
                    <div class="form-group">
                        <label for="telefono">Teléfono <span class="required">*</span></label>
                        <input type="tel" id="telefono" name="telefono" 
                               value="${lector.telefono}" required maxlength="20">
                    </div>
                    
                    <!-- Dirección -->
                    <div class="form-group">
                        <label for="direccion">Dirección <span class="required">*</span></label>
                        <textarea id="direccion" name="direccion" rows="3" required>${lector.direccion}</textarea>
                    </div>
                    
                    <!-- ✅ Fecha Registro (EDITABLE en modo editar) -->
                    <c:if test="${modo == 'editar'}">
                        <div class="form-group">
                            <label for="fechaRegistro">Fecha Registro</label>
                            <input type="datetime-local" id="fechaRegistro" name="fechaRegistro"
                                   value="<fmt:formatDate value='${lector.fechaRegistro}' pattern='yyyy-MM-dd\'T\'HH:mm'/>">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - fecha y hora en que se registró el lector
                            </small>
                        </div>
                    </c:if>
                    
                    <!-- Estado (editable en modo editar) -->
                    <c:if test="${modo == 'editar'}">
                        <div class="form-group">
                            <label for="estado">Estado <span class="required">*</span></label>
                            <select id="estado" name="estado" required>
                                <option value="ACTIVO" ${lector.estado == 'ACTIVO' ? 'selected' : ''}>
                                    Activo
                                </option>
                                <option value="INACTIVO" ${lector.estado == 'INACTIVO' ? 'selected' : ''}>
                                    Inactivo
                                </option>
                                <option value="SUSPENDIDO" ${lector.estado == 'SUSPENDIDO' ? 'selected' : ''}>
                                    Suspendido
                                </option>
                            </select>
                        </div>
                    </c:if>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ✅ Guardar Lector
                        </button>
                        <a href="ControladorSistema?action=listarLectores" class="btn btn-secondary">
                            ❌ Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>