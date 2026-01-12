<%-- 
    Document   : form_lector
    Created on : 09/01/2026, 15:42:01
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="guardarLector">
                    <input type="hidden" name="modo" value="${modo}">
                    <c:if test="${modo == 'editar'}">
                        <input type="hidden" name="id" value="${lector.idLector}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="nombre">Nombre: *</label>
                        <input type="text" id="nombre" name="nombre" 
                               value="${lector.nombre}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="apellido">Apellido: *</label>
                        <input type="text" id="apellido" name="apellido" 
                               value="${lector.apellido}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="cedula">Cédula: *</label>
                        <input type="text" id="cedula" name="cedula" 
                               value="${lector.cedula}" required maxlength="20">
                    </div>
                    
                    <div class="form-group">
                        <label for="correo">Correo Electrónico: *</label>
                        <input type="email" id="correo" name="correo" 
                               value="${lector.correo}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="telefono">Teléfono: *</label>
                        <input type="tel" id="telefono" name="telefono" 
                               value="${lector.telefono}" required maxlength="20">
                    </div>
                    
                    <div class="form-group">
                        <label for="direccion">Dirección: *</label>
                        <textarea id="direccion" name="direccion" rows="3" required>${lector.direccion}</textarea>
                    </div>
                    
                    <c:if test="${modo == 'editar'}">
                        <div class="form-group">
                            <label for="estado">Estado: *</label>
                            <select id="estado" name="estado" required>
                                <option value="ACTIVO" ${lector.estado == 'ACTIVO' ? 'selected' : ''}>
                                    Activo
                                </option>
                                <option value="INACTIVO" ${lector.estado == 'INACTIVO' ? 'selected' : ''}>
                                    Inactivo
                                </option>
                            </select>
                        </div>
                    </c:if>
                    
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