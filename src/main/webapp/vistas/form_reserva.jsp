<%-- 
    Document   : form_reserva
    Created on : 09/01/2026, 15:43:38
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nueva Reserva - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Crear Nueva Reserva</h1>
                <p class="breadcrumb">Dashboard / Reservas / Nueva</p>
            </div>
            
            <div class="table-container">
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="crearReserva">
                    
                    <div class="form-group">
                        <label for="libro">Libro: *</label>
                        <select id="libro" name="libro" required>
                            <option value="">Seleccione un libro</option>
                            <c:forEach var="libro" items="${libros}">
                                <option value="${libro.idLibro}">
                                    ${libro.titulo} - ${libro.autor} 
                                    (${libro.copiasDisponibles > 0 ? 'Disponible' : 'No disponible'})
                                </option>
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
                            <strong>Información de la Reserva:</strong><br>
                            • La reserva se creará con la fecha actual<br>
                            • Tendrá una validez de 2 días<br>
                            • El lector puede tener máximo 5 reservas activas<br>
                            • La reserva expirará automáticamente si no se retira el libro
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            ✅ Crear Reserva
                        </button>
                        <a href="ControladorSistema?action=listarReservas" class="btn btn-secondary">
                            ❌ Cancelar
                        </a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>