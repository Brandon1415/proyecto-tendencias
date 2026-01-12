<%-- 
    Document   : form_libro
    Created on : 09/01/2026, 15:42:37
    Author     : ASUS
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
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="guardarLibro">
                    <input type="hidden" name="modo" value="${modo}">
                    <c:if test="${modo == 'editar'}">
                        <input type="hidden" name="id" value="${libro.idLibro}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="titulo">Título: *</label>
                        <input type="text" id="titulo" name="titulo" 
                               value="${libro.titulo}" required maxlength="255">
                    </div>
                    
                    <div class="form-group">
                        <label for="autor">Autor: *</label>
                        <input type="text" id="autor" name="autor" 
                               value="${libro.autor}" required maxlength="200">
                    </div>
                    
                    <div class="form-group">
                        <label for="isbn">ISBN: *</label>
                        <input type="text" id="isbn" name="isbn" 
                               value="${libro.isbn}" required maxlength="20">
                    </div>
                    
                    <div class="form-group">
                        <label for="categoria">Categoría: *</label>
                        <select id="categoria" name="categoria" required>
                            <option value="">Seleccione una categoría</option>
                            <c:forEach var="cat" items="${categorias}">
                                <option value="${cat.idCategoria}" 
                                        ${libro.idCategoria == cat.idCategoria ? 'selected' : ''}>
                                    ${cat.nombre}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="editorial">Editorial: *</label>
                        <input type="text" id="editorial" name="editorial" 
                               value="${libro.editorial}" required maxlength="150">
                    </div>
                    
                    <div class="form-group">
                        <label for="anio">Año de Publicación: *</label>
                        <input type="number" id="anio" name="anio" 
                               value="${libro.anioPublicacion}" required 
                               min="1000" max="2100">
                    </div>
                    
                    <div class="form-group">
                        <label for="copias">Cantidad de Copias: *</label>
                        <input type="number" id="copias" name="copias" 
                               value="${libro.copiasTotales}" required 
                               min="1" max="1000">
                        <small style="color: #7f8c8d; font-size: 0.85rem;">
                            Debe ser mayor o igual a 5 copias
                        </small>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ✅ Guardar Libro
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