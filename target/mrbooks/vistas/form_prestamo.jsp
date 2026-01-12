<%-- 
    Document   : form_prestamo
    Created on : 09/01/2026, 15:43:08
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo Préstamo - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Registrar Nuevo Préstamo</h1>
                <p class="breadcrumb">Dashboard / Préstamos / Nuevo</p>
            </div>
            
            <div class="table-container">
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="registrarPrestamo">
                    
                    <div class="form-group">
                        <label for="libro">Libro: *</label>
                        <select id="libro" name="libro" required>
                            <option value="">Seleccione un libro</option>
                            <c:forEach var="libro" items="${libros}">
                                <c:if test="${libro.copiasDisponibles > 0}">
                                    <option value="${libro.idLibro}">
                                        ${libro.titulo} - ${libro.autor} (Disponibles: ${libro.copiasDisponibles})
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="lector">Lector: *</label>
                        <select id="lector" name="lector" required>
                            <option value="">Seleccione un lector</option>
                            <c:forEach var="lector" items="${lectores}">
                                <option value="${lector.idLector}">
                                    ${lector.nombreCompleto} - ${lector.cedula}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="alert alert-success">
                        <span>ℹ️</span>
                        <div>
                            <strong>Información del Préstamo:</strong><br>
                            • El préstamo se registrará con la fecha actual<br>
                            • La fecha de devolución será en 15 días<br>
                            • El lector recibirá una notificación de las fechas
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ✅ Registrar Préstamo
                        </button>
                        <a href="ControladorSistema?action=listarPrestamos" class="btn btn-secondary">
                            ❌ Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>