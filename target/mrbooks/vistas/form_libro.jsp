<%-- 
    Document   : form_libro
    Formulario para crear y editar libros
    ✅ ACTUALIZADO: Ahora permite editar "Activo" en la edición
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${modo == 'nuevo' ? 'Nuevo' : 'Editar'} Libro - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>${modo == 'nuevo' ? 'Nuevo' : 'Editar'} Libro</h1>
                <p class="breadcrumb">Dashboard / Libros / ${modo == 'nuevo' ? 'Nuevo' : 'Editar'}</p>
            </div>
            
            <div class="table-container">
                <!-- Información del libro (si es edición) -->
                <c:if test="${modo == 'editar'}">
                    <div class="info-box">
                        <h3>📖 Información del Libro</h3>
                        <p><strong>ID Libro:</strong> ${libro.idLibro}</p>
                        <p><strong>Fecha Registro:</strong> ${libro.fechaRegistro}</p>
                        <p><strong>Fecha Modificación:</strong> ${libro.fechaModificacion}</p>
                    </div>
                </c:if>
                
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="guardarLibro">
                    <input type="hidden" name="modo" value="${modo}">
                    <c:if test="${modo == 'editar'}">
                        <input type="hidden" name="id" value="${libro.idLibro}">
                    </c:if>
                    
                    <!-- Título -->
                    <div class="form-group">
                        <label for="titulo">Título <span class="required">*</span></label>
                        <input type="text" id="titulo" name="titulo" 
                               value="${libro.titulo}" required maxlength="255">
                    </div>
                    
                    <!-- Autor -->
                    <div class="form-group">
                        <label for="autor">Autor <span class="required">*</span></label>
                        <input type="text" id="autor" name="autor" 
                               value="${libro.autor}" required maxlength="200">
                    </div>
                    
                    <!-- ISBN -->
                    <div class="form-group">
                        <label for="isbn">ISBN <span class="required">*</span></label>
                        <input type="text" id="isbn" name="isbn" 
                               value="${libro.isbn}" required maxlength="20">
                    </div>
                    
                    <!-- Categoría (TEXTO, NO DROPDOWN) -->
                    <div class="form-group">
                        <label for="categoria">Categoría <span class="required">*</span></label>
                        <input type="text" id="categoria" name="categoria" 
                               value="${libro.categoria}" required maxlength="100"
                               placeholder="Ej: Ficción, Ciencia, Historia, Educación, Tecnología">
                        <small style="color: #7f8c8d; font-size: 0.85rem;">
                            Ingresa el nombre de la categoría (Ficción, Ciencia, Historia, Educación, Tecnología, etc.)
                        </small>
                    </div>
                    
                    <!-- Editorial -->
                    <div class="form-group">
                        <label for="editorial">Editorial <span class="required">*</span></label>
                        <input type="text" id="editorial" name="editorial" 
                               value="${libro.editorial}" required maxlength="150">
                    </div>
                    
                    <!-- Año de Publicación -->
                    <div class="form-group">
                        <label for="anio">Año de Publicación <span class="required">*</span></label>
                        <input type="number" id="anio" name="anio" 
                               value="${libro.anioPublicacion}" required 
                               min="1000" max="2100">
                    </div>
                    
                    <!-- Cantidad de Copias Total -->
                    <div class="form-group">
                        <label for="copias">Cantidad de Copias Totales <span class="required">*</span></label>
                        <input type="number" id="copias" name="copias" 
                               value="${libro.copiasTotales}" required 
                               min="1" max="1000">
                        <small style="color: #7f8c8d; font-size: 0.85rem;">
                            Número total de copias del libro
                        </small>
                    </div>
                                       
                    <!-- ✅ ACTIVO (EDITABLE SOLO EN EDICIÓN) -->
                    <c:if test="${modo == 'editar'}">
                        <div class="form-group">
                            <label for="activo">Estado <span class="required">*</span></label>
                            <select id="activo" name="activo" required>
                                <option value="">-- Seleccione --</option>
                                <option value="true" ${libro.activo ? 'selected' : ''}>✅ ACTIVO</option>
                                <option value="false" ${!libro.activo ? 'selected' : ''}>❌ INACTIVO</option>
                            </select>
                            <small style="color: #7f8c8d; font-size: 0.85rem;">
                                Cambia el estado del libro (sin afectar el historial de bajas)
                            </small>
                        </div>
                    </c:if>
                    
                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            💾 Guardar Libro
                        </button>
                        <a href="ControladorSistema?action=listarLibros" class="btn btn-secondary">
                            ❌ Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>