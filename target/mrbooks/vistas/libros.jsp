<%-- 
    Document   : libros
    Created on : 09/01/2026, 13:06:07
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Libros - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Gestión de Libros</h1>
                <p class="breadcrumb">Dashboard / Libros</p>
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
                <a href="ControladorSistema?action=nuevoLibro" class="btn btn-primary">
                    ➕ Nuevo Libro
                </a>
                <a href="ControladorSistema?action=verHistorialBajas" class="btn btn-secondary">
                    📋 Historial Bajas
                </a>
                
                <form action="ControladorSistema" method="get" class="search-form">
                    <input type="hidden" name="action" value="buscarLibros">
                    <input type="text" name="criterio" placeholder="Buscar..." value="${criterio}" class="search-input">
                    <button type="submit" class="btn btn-secondary">🔍 Buscar</button>
                </form>
            </div>
            
            <div class="table-container">
                <h2>Catálogo de Libros</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Título</th>
                            <th>Autor</th>
                            <th>ISBN</th>
                            <th>Categoría</th>
                            <th>Año</th>
                            <th>Total</th>
                            <th>Disponibles</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="libro" items="${libros}">
                            <tr>
                                <td>${libro.idLibro}</td>
                                <td><strong>${libro.titulo}</strong></td>
                                <td>${libro.autor}</td>
                                <td>${libro.isbn}</td>
                                <td>${libro.nombreCategoria}</td>
                                <td>${libro.anioPublicacion}</td>
                                <td>${libro.copiasTotales}</td>
                                <td>
                                    <span class="badge ${libro.copiasDisponibles > 0 ? 'badge-success' : 'badge-warning'}">
                                        ${libro.copiasDisponibles}
                                    </span>
                                </td>
                                <td>
                                    <span class="badge ${libro.estadoLibro == 'DISPONIBLE' ? 'badge-success' : 
                                                         libro.estadoLibro == 'PRESTADO' ? 'badge-warning' : 'badge-danger'}">
                                        ${libro.estadoLibro}
                                    </span>
                                </td>
                                <td class="actions">
                                    <a href="ControladorSistema?action=editarLibro&id=${libro.idLibro}" 
                                       class="btn-icon btn-edit" title="Editar">✏️</a>
                                    <a href="#" class="btn-icon btn-warning" title="Dar Baja"
                                       onclick="darBajaLibro(${libro.idLibro}); return false;">❌</a>
                                    <a href="ControladorSistema?action=eliminarLibro&id=${libro.idLibro}" 
                                       class="btn-icon btn-delete" title="Eliminar Permanente"
                                       onclick="return confirm('⚠️ ¿Estás seguro de ELIMINAR este libro PERMANENTEMENTE?\n\nEsta acción NO se puede deshacer y se perderá TODO el historial.\n\nLibro: ${libro.titulo}\nISBN: ${libro.isbn}\n\n¿Prefieres dar de BAJA en lugar de eliminar?')">🗑️</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <c:if test="${empty libros}">
                    <div class="empty-state">
                        <p>😕 No se encontraron libros</p>
                    </div>
                </c:if>
            </div>
        </main>
    </div>
    
    <!-- Modal Baja -->
    <div id="modalBaja" class="modal">
        <div class="modal-content">
            <span class="close" onclick="cerrarModal()">&times;</span>
            <h2>Dar de Baja Libro</h2>
            <form action="ControladorSistema" method="post">
                <input type="hidden" name="action" value="darBajaLibro">
                <input type="hidden" name="id" id="idLibroBaja">
                
                <div class="form-group">
                    <label>Motivo:</label>
                    <select name="motivo" required>
                        <option value="">Seleccione</option>
                        <option value="DETERIORADO">Deteriorado</option>
                        <option value="PERDIDO">Perdido</option>
                        <option value="OBSOLETO">Obsoleto</option>
                        <option value="OTRO">Otro</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Descripción:</label>
                    <textarea name="descripcion" rows="4" required></textarea>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-danger">Confirmar Baja</button>
                    <button type="button" class="btn btn-secondary" onclick="cerrarModal()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        function darBajaLibro(id) {
            document.getElementById('idLibroBaja').value = id;
            document.getElementById('modalBaja').style.display = 'block';
        }
        
        function cerrarModal() {
            document.getElementById('modalBaja').style.display = 'none';
        }
        
        window.onclick = function(e) {
            const modal = document.getElementById('modalBaja');
            if (e.target == modal) cerrarModal();
        }
    </script>
</body>
</html>