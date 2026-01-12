<%-- 
    Document   : reportes
    Created on : 09/01/2026, 13:07:03
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reportes - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Reportes y Estadísticas</h1>
                <p class="breadcrumb">Dashboard / Reportes</p>
            </div>
            
            <div class="reportes-grid">
                <a href="ControladorSistema?action=reporteLibrosMasPrestados" class="reporte-card">
                    <span class="reporte-icon">📚</span>
                    <h3>Libros Más Prestados</h3>
                    <p>Top 10 de libros más solicitados</p>
                </a>
                
                <a href="ControladorSistema?action=reportePrestamosAtrasados" class="reporte-card">
                    <span class="reporte-icon">⚠️</span>
                    <h3>Préstamos Atrasados</h3>
                    <p>Lista completa de préstamos vencidos</p>
                </a>
                
                <a href="ControladorSistema?action=reporteEstadisticasLectores" class="reporte-card">
                    <span class="reporte-icon">👥</span>
                    <h3>Estadísticas Lectores</h3>
                    <p>Actividad y comportamiento por lector</p>
                </a>
            </div>
            
            <c:if test="${not empty tipoReporte}">
                <div class="table-container">
                    <c:choose>
                        <c:when test="${tipoReporte == 'masPrestados'}">
                            <h2>📚 Top 10 - Libros Más Prestados</h2>
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Título</th>
                                        <th>Autor</th>
                                        <th>ISBN</th>
                                        <th>Categoría</th>
                                        <th>Total Préstamos</th>
                                        <th>Disponibles</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="libro" items="${libros}" varStatus="status">
                                        <tr>
                                            <td><strong>${status.index + 1}</strong></td>
                                            <td>${libro.titulo}</td>
                                            <td>${libro.autor}</td>
                                            <td>${libro.isbn}</td>
                                            <td>${libro.categoria}</td>
                                            <td><span class="badge badge-primary">${libro.total_prestamos}</span></td>
                                            <td>${libro.copias_disponibles}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        
                        <c:when test="${tipoReporte == 'atrasados'}">
                            <h2>⚠️ Préstamos Atrasados</h2>
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Libro</th>
                                        <th>Lector</th>
                                        <th>Cédula</th>
                                        <th>Fecha Préstamo</th>
                                        <th>Fecha Devolución</th>
                                        <th>Días Retraso</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="prestamo" items="${prestamos}">
                                        <tr>
                                            <td>${prestamo.id_prestamo}</td>
                                            <td><strong>${prestamo.libro}</strong></td>
                                            <td>${prestamo.lector}</td>
                                            <td>${prestamo.cedula}</td>
                                            <td>${prestamo.fecha_prestamo}</td>
                                            <td>${prestamo.fecha_devolucion_esperada}</td>
                                            <td><span class="badge badge-danger">${prestamo.dias_retraso} días</span></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        
                        <c:when test="${tipoReporte == 'estadisticasLectores'}">
                            <h2>👥 Estadísticas por Lector</h2>
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Lector</th>
                                        <th>Cédula</th>
                                        <th>Correo</th>
                                        <th>Total</th>
                                        <th>Activos</th>
                                        <th>Devueltos</th>
                                        <th>Atrasados</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="stats" items="${estadisticas}">
                                        <tr>
                                            <td>${stats.id_lector}</td>
                                            <td><strong>${stats.lector}</strong></td>
                                            <td>${stats.cedula}</td>
                                            <td>${stats.correo}</td>
                                            <td><span class="badge badge-primary">${stats.total_prestamos}</span></td>
                                            <td><span class="badge badge-warning">${stats.prestamos_activos}</span></td>
                                            <td><span class="badge badge-success">${stats.prestamos_devueltos}</span></td>
                                            <td><span class="badge badge-danger">${stats.prestamos_atrasados}</span></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                    </c:choose>
                </div>
            </c:if>
        </main>
    </div>
</body>
</html>