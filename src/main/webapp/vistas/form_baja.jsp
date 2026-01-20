<%-- 
    Document   : form_baja
    ✅ ACTUALIZADO: Permite editar LITERALMENTE TODO
    - id_libro (dropdown)
    - id_usuario (dropdown)
    - motivo, descripcion, fechas
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Baja - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Editar Registro de Baja</h1>
                <p class="breadcrumb">Dashboard / Libros / Historial Bajas / Editar</p>
            </div>
            
            <div class="form-container">
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="actualizarBaja">
                    <input type="hidden" name="id" value="${baja.idBaja}">
                    
                    <!-- ✅ Información del Libro (EDITABLE - DROPDOWN) -->
                    <div class="form-section">
                        <h3>📚 Información del Libro</h3>
                        
                        <!-- Libro (EDITABLE) -->
                        <div class="form-group">
                            <label for="libro">Libro <span class="required">*</span></label>
                            <select id="libro" name="libro" required onchange="actualizarDatosLibro()">
                                <option value="">-- Seleccione un libro --</option>
                                <c:forEach var="libro" items="${libros}">
                                    <option value="${libro.idLibro}" 
                                            data-titulo="${libro.titulo}"
                                            data-autor="${libro.autor}"
                                            data-isbn="${libro.isbn}"
                                            ${baja.idLibro == libro.idLibro ? 'selected' : ''}>
                                        ${libro.idLibro} - ${libro.titulo}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <!-- Título (lectura, se actualiza con JS) -->
                        <div class="form-group">
                            <label>Título:</label>
                            <input type="text" id="titulo" readonly class="readonly-input">
                        </div>
                        
                        <!-- Autor (lectura, se actualiza con JS) -->
                        <div class="form-group">
                            <label>Autor:</label>
                            <input type="text" id="autor" readonly class="readonly-input">
                        </div>
                        
                        <!-- ISBN (lectura, se actualiza con JS) -->
                        <div class="form-group">
                            <label>ISBN:</label>
                            <input type="text" id="isbn" readonly class="readonly-input">
                        </div>
                    </div>
                    
                    <!-- ✅ Información del Usuario (EDITABLE - DROPDOWN) -->
                    <div class="form-section">
                        <h3>👤 Usuario que Registró</h3>
                        
                        <div class="form-group">
                            <label for="usuario">Usuario <span class="required">*</span></label>
                            <select id="usuario" name="usuario" required>
                                <option value="">-- Seleccione un usuario --</option>
                                <c:forEach var="usuario" items="${usuarios}">
                                    <option value="${usuario.idUsuario}" 
                                            ${baja.idUsuario == usuario.idUsuario ? 'selected' : ''}>
                                        ${usuario.idUsuario} - ${usuario.nombreCompleto} (${usuario.rol})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <!-- ✅ Información de la Baja (EDITABLE) -->
                    <div class="form-section">
                        <h3>📋 Información de la Baja</h3>
                        
                        <!-- Motivo (EDITABLE) -->
                        <div class="form-group">
                            <label for="motivo">Motivo <span class="required">*</span></label>
                            <select id="motivo" name="motivo" required>
                                <option value="">Seleccione un motivo</option>
                                <option value="DETERIORADO" ${baja.motivo == 'DETERIORADO' ? 'selected' : ''}>Deteriorado</option>
                                <option value="PERDIDO" ${baja.motivo == 'PERDIDO' ? 'selected' : ''}>Perdido</option>
                                <option value="OBSOLETO" ${baja.motivo == 'OBSOLETO' ? 'selected' : ''}>Obsoleto</option>
                                <option value="OTRO" ${baja.motivo == 'OTRO' ? 'selected' : ''}>Otro</option>
                            </select>
                        </div>
                        
                        <!-- Descripción (EDITABLE) -->
                        <div class="form-group">
                            <label for="descripcion">Descripción <span class="required">*</span></label>
                            <textarea id="descripcion" name="descripcion" rows="4" 
                                      required placeholder="Describa el motivo de la baja...">${baja.descripcion}</textarea>
                        </div>
                        
                        <!-- Fecha de Baja (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaBaja">Fecha de Baja <span class="required">*</span></label>
                            <input type="date" id="fechaBaja" name="fechaBaja" 
                                   value="${baja.fechaBaja}" required>
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - fecha en que se dio de baja el libro
                            </small>
                        </div>
                        
                        <!-- Fecha Registro (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaRegistro">Fecha Registro</label>
                            <input type="datetime-local" id="fechaRegistro" name="fechaRegistro"
                                   value="<fmt:formatDate value='${baja.fechaRegistro}' pattern='yyyy-MM-dd\'T\'HH:mm'/>">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - fecha y hora en que se registró la baja
                            </small>
                        </div>
                        
                        <!-- Fecha Modificación (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaModificacion">Fecha Modificación</label>
                            <input type="datetime-local" id="fechaModificacion" name="fechaModificacion"
                                   value="<fmt:formatDate value='${baja.fechaModificacion}' pattern='yyyy-MM-dd\'T\'HH:mm'/>">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - última fecha de modificación
                            </small>
                        </div>
                    </div>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">💾 Guardar Cambios</button>
                        <a href="ControladorSistema?action=verHistorialBajas" class="btn btn-secondary">❌ Cancelar</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
    
    <!-- Script para actualizar datos del libro dinámicamente -->
    <script>
        function actualizarDatosLibro() {
            const select = document.getElementById('libro');
            const option = select.options[select.selectedIndex];
            
            document.getElementById('titulo').value = option.getAttribute('data-titulo') || '';
            document.getElementById('autor').value = option.getAttribute('data-autor') || '';
            document.getElementById('isbn').value = option.getAttribute('data-isbn') || '';
        }
        
        // Ejecutar al cargar la página para mostrar los datos del libro actual
        window.onload = function() {
            actualizarDatosLibro();
        }
    </script>
</body>
</html>