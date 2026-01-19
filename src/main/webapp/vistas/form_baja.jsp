<%-- 
    Document   : form_baja
    ✅ ACTUALIZADO: Permite editar LITERALMENTE TODO (motivo, descripcion, fechas)
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    
                    <!-- Información del Libro (solo lectura) -->
                    <div class="form-section">
                        <h3>📚 Información del Libro</h3>
                        <div class="form-row">
                            <div class="form-group">
                                <label>Título:</label>
                                <input type="text" value="${baja.tituloLibro}" readonly class="readonly-input">
                            </div>
                            <div class="form-group">
                                <label>Autor:</label>
                                <input type="text" value="${baja.autorLibro}" readonly class="readonly-input">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label>ISBN:</label>
                                <input type="text" value="${baja.isbnLibro}" readonly class="readonly-input">
                            </div>
                            <div class="form-group">
                                <label>Registrado Por:</label>
                                <input type="text" value="${baja.nombreUsuario}" readonly class="readonly-input">
                            </div>
                        </div>
                    </div>
                    
                    <!-- Información de la Baja (editable) -->
                    <div class="form-section">
                        <h3>📋 Información de la Baja</h3>
                        
                        <!-- ✅ Motivo (EDITABLE) -->
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
                        
                        <!-- ✅ Descripción (EDITABLE) -->
                        <div class="form-group">
                            <label for="descripcion">Descripción <span class="required">*</span></label>
                            <textarea id="descripcion" name="descripcion" rows="4" 
                                      required placeholder="Describa el motivo de la baja...">${baja.descripcion}</textarea>
                        </div>
                        
                        <!-- ✅ Fecha de Baja (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaBaja">Fecha de Baja <span class="required">*</span></label>
                            <input type="date" id="fechaBaja" name="fechaBaja" 
                                   value="${baja.fechaBaja}" required>
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - fecha en que se dio de baja el libro
                            </small>
                        </div>
                        
                        <!-- ✅ Fecha Registro (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaRegistro">Fecha Registro</label>
                            <input type="datetime-local" id="fechaRegistro" name="fechaRegistro"
                                   value="${baja.fechaRegistro}">
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Editable - fecha y hora en que se registró la baja
                            </small>
                        </div>
                        
                        <!-- ✅ Fecha Modificación (EDITABLE) -->
                        <div class="form-group">
                            <label for="fechaModificacion">Fecha Modificación</label>
                            <input type="datetime-local" id="fechaModificacion" name="fechaModificacion"
                                   value="${baja.fechaModificacion}">
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
</body>
</html>