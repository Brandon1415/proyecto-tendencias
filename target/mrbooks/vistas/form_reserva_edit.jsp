<%-- 
    Formulario para editar una reserva existente
    ✅ EDITA LITERALMENTE TODOS LOS CAMPOS DE LA TABLA
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Reserva - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>✏️ Editar Reserva #${reserva.idReserva}</h1>
                <p class="breadcrumb">Dashboard / Reservas / Editar</p>
            </div>
            
            <div class="form-container">
                <!-- Información de la reserva actual -->
                <div class="info-box">
                    <h3>📋 Información Actual de la Reserva</h3>
                    <p><strong>ID Reserva:</strong> ${reserva.idReserva}</p>
                    <p><strong>Estado Actual:</strong> ${reserva.estado}</p>
                    <p><strong>Registrado:</strong> 
                        <fmt:formatDate value="${reserva.fechaRegistro}" pattern="dd/MM/yyyy HH:mm:ss" />
                    </p>
                    <p><strong>Última Modificación:</strong> 
                        <fmt:formatDate value="${reserva.fechaModificacion}" pattern="dd/MM/yyyy HH:mm:ss" />
                    </p>
                </div>
                
                <!-- Formulario de edición -->
                <form action="ControladorSistema" method="POST">
                    <input type="hidden" name="action" value="actualizarReserva">
                    <input type="hidden" name="id" value="${reserva.idReserva}">
                    
                    <!-- SECCIÓN 1: Datos Principales -->
                    <div class="form-section">
                        <h3>📚 Datos de la Reserva</h3>
                        
                        <!-- Estado -->
                        <div class="form-group">
                            <label for="estado">
                                Estado <span class="required">*</span>
                            </label>
                            <select id="estado" name="estado" required>
                                <option value="PENDIENTE" 
                                    <c:if test="${reserva.estado == 'PENDIENTE'}">selected</c:if>>
                                    🕐 PENDIENTE
                                </option>
                                <option value="COMPLETADA" 
                                    <c:if test="${reserva.estado == 'COMPLETADA'}">selected</c:if>>
                                    ✅ COMPLETADA
                                </option>
                                <option value="CANCELADA" 
                                    <c:if test="${reserva.estado == 'CANCELADA'}">selected</c:if>>
                                    ❌ CANCELADA
                                </option>
                            </select>
                            <span class="help-text">Estado actual de la reserva</span>
                        </div>
                        
                        <!-- Libro y Lector en fila -->
                        <div class="form-row">
                            <!-- Seleccionar Libro -->
                            <div class="form-group">
                                <label for="libro">
                                    📖 Libro <span class="required">*</span>
                                </label>
                                <select id="libro" name="libro" required>
                                    <option value="">-- Selecciona un libro --</option>
                                    <c:forEach var="libro" items="${libros}">
                                        <option value="${libro.idLibro}" 
                                            <c:if test="${libro.idLibro == reserva.idLibro}">selected</c:if>>
                                            ${libro.titulo} (ISBN: ${libro.isbn})
                                        </option>
                                    </c:forEach>
                                </select>
                                <span class="help-text">Libro reservado</span>
                            </div>
                            
                            <!-- Seleccionar Lector -->
                            <div class="form-group">
                                <label for="lector">
                                    👤 Lector <span class="required">*</span>
                                </label>
                                <select id="lector" name="lector" required>
                                    <option value="">-- Selecciona un lector --</option>
                                    <c:forEach var="lector" items="${lectores}">
                                        <option value="${lector.idLector}"
                                            <c:if test="${lector.idLector == reserva.idLector}">selected</c:if>>
                                            ${lector.nombre} ${lector.apellido} (${lector.cedula})
                                        </option>
                                    </c:forEach>
                                </select>
                                <span class="help-text">Lector que realizó la reserva</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- SECCIÓN 2: Fechas de Reserva -->
                    <div class="form-section">
                        <h3>📅 Fechas de la Reserva</h3>
                        
                        <div class="form-row">
                            <!-- Fecha de Reserva -->
                            <div class="form-group">
                                <label for="fechaReserva">
                                    📅 Fecha de Reserva <span class="required">*</span>
                                </label>
                                <input type="date" 
                                       id="fechaReserva" 
                                       name="fechaReserva" 
                                       value="${reserva.fechaReserva}"
                                       required>
                                <span class="help-text">Fecha en que se realizó la reserva</span>
                            </div>
                            
                            <!-- Fecha de Expiración -->
                            <div class="form-group">
                                <label for="fechaExpiracion">
                                    ⏰ Fecha de Expiración <span class="required">*</span>
                                </label>
                                <input type="date" 
                                       id="fechaExpiracion" 
                                       name="fechaExpiracion" 
                                       value="${reserva.fechaExpiracion}"
                                       required>
                                <span class="help-text">Fecha límite de la reserva</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- SECCIÓN 3: Timestamps del Sistema (Editables) -->
                    <div class="form-section">
                        <h3>🕒 Timestamps del Sistema (Avanzado)</h3>
                        <p style="color: #856404; background: #fff3cd; padding: 10px; border-radius: 5px; font-size: 0.9em;">
                            ⚠️ <strong>Advertencia:</strong> Estos campos normalmente se gestionan automáticamente. 
                            Edítalos solo si necesitas corregir datos históricos.
                        </p>
                        
                        <div class="form-row">
                            <!-- Fecha de Registro -->
                            <div class="form-group">
                                <label for="fechaRegistro">
                                    🕐 Fecha de Registro
                                </label>
                                <fmt:formatDate value="${reserva.fechaRegistro}" 
                                                pattern="yyyy-MM-dd'T'HH:mm" 
                                                var="fechaRegistroFormato" />
                                <input type="datetime-local" 
                                       id="fechaRegistro" 
                                       name="fechaRegistro" 
                                       value="${fechaRegistroFormato}">
                                <span class="help-text">Cuándo se creó este registro en el sistema</span>
                            </div>
                            
                            <!-- Fecha de Modificación -->
                            <div class="form-group">
                                <label for="fechaModificacion">
                                    🕑 Fecha de Modificación
                                </label>
                                <fmt:formatDate value="${reserva.fechaModificacion}" 
                                                pattern="yyyy-MM-dd'T'HH:mm" 
                                                var="fechaModificacionFormato" />
                                <input type="datetime-local" 
                                       id="fechaModificacion" 
                                       name="fechaModificacion" 
                                       value="${fechaModificacionFormato}">
                                <span class="help-text">Última vez que se modificó este registro</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            💾 Guardar Cambios
                        </button>
                        <a href="ControladorSistema?action=listarReservas" class="btn btn-secondary">
                            ❌ Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>
    
    <script>
        // Validación: fecha de expiración debe ser después de fecha de reserva
        document.querySelector('form').addEventListener('submit', function(e) {
            const fechaReserva = new Date(document.getElementById('fechaReserva').value);
            const fechaExpiracion = new Date(document.getElementById('fechaExpiracion').value);
            
            if (fechaExpiracion <= fechaReserva) {
                e.preventDefault();
                alert('⚠️ La fecha de expiración debe ser posterior a la fecha de reserva');
                return false;
            }
            
            // Validación de timestamps (opcional)
            const fechaRegistro = document.getElementById('fechaRegistro').value;
            const fechaModificacion = document.getElementById('fechaModificacion').value;
            
            if (fechaRegistro && fechaModificacion) {
                const registro = new Date(fechaRegistro);
                const modificacion = new Date(fechaModificacion);
                
                if (modificacion < registro) {
                    const confirmar = confirm('⚠️ La fecha de modificación es anterior a la de registro. ¿Deseas continuar?');
                    if (!confirmar) {
                        e.preventDefault();
                        return false;
                    }
                }
            }
        });
        
        // Actualizar automáticamente la fecha de modificación al momento actual (opcional)
        document.getElementById('actualizarFechaModificacion')?.addEventListener('click', function() {
            const ahora = new Date();
            const año = ahora.getFullYear();
            const mes = String(ahora.getMonth() + 1).padStart(2, '0');
            const dia = String(ahora.getDate()).padStart(2, '0');
            const hora = String(ahora.getHours()).padStart(2, '0');
            const minuto = String(ahora.getMinutes()).padStart(2, '0');
            
            const fechaActual = `${año}-${mes}-${dia}T${hora}:${minuto}`;
            document.getElementById('fechaModificacion').value = fechaActual;
        });
    </script>
</body>
</html>