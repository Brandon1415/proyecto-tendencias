<%-- 
    Document   : historial_lector
    Created on : 09/01/2026, 15:44:31
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Bajas - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <%@ include file="includes/sidebar.jsp" %>
        
        <main class="main-content">
            <div class="content-header">
                <h1>Historial de Bajas de Libros</h1>
                <p class="breadcrumb">Dashboard / Libros / Historial de Bajas</p>
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
            
            <div class="table-container">
                <h2>📋 Registro de Libros Dados de Baja</h2>
                
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>NUM BAJA</th>
                            <th>LIBRO</th>
                            <th>AUTOR</th>
                            <th>ISBN</th>
                            <th>MOTIVO</th>
                            <th>DESCRIPCIÓN</th>
                            <th>REGISTRADO POR</th>
                            <th>FECHA DE BAJA</th>
                            <th>FECHA DE REGISTRO</th>
                            <th>ACCIONES</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="baja" items="${bajas}">
                            <tr>
                                <td>${baja.id_baja}</td>
                                <td><strong>${baja.libro}</strong></td>
                                <td>${baja.autor}</td>
                                <td>${baja.isbn}</td>
                                <td>
                                    <span class="badge ${baja.motivo == 'DETERIORADO' ? 'badge-warning' : 
                                                         baja.motivo == 'PERDIDO' ? 'badge-danger' : 
                                                         baja.motivo == 'OBSOLETO' ? 'badge-secondary' : 'badge-primary'}">
                                        ${baja.motivo}
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty baja.descripcion}">
                                            ${baja.descripcion}
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #7f8c8d; font-style: italic;">Sin descripción</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${baja.registrado_por}</td>
                                <td>${baja.fecha_baja}</td>
                                <td>${baja.fecha_registro}</td>
                                <td class="actions">
                                    <a href="ControladorSistema?action=editarBaja&id=${baja.id_baja}" 
                                       class="btn-icon btn-edit" title="Editar">✏️</a>
                                    <a href="ControladorSistema?action=eliminarBaja&id=${baja.id_baja}" 
                                       class="btn-icon btn-delete" title="Eliminar"
                                       onclick="return confirm('⚠️ ¿Estás seguro de ELIMINAR este registro de baja?\n\nEsta acción NO se puede deshacer.\n\nLibro: ${baja.libro}\nMotivo: ${baja.motivo}')">🗑️</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <c:if test="${empty bajas}">
                    <div class="empty-state">
                        <p>✅ No hay libros dados de baja</p>
                    </div>
                </c:if>
            </div>
            
            <!-- Estadísticas de Bajas -->
            <c:if test="${not empty bajas}">
                <div class="stats-section">
                    <h2>📊 Estadísticas de Bajas</h2>
                    <div class="stats-grid">
                        <div class="stat-item">
                            <span class="stat-label">Total Bajas Registradas</span>
                            <span class="stat-value">${bajas.size()}</span>
                        </div>
                        <div class="stat-item">
                            <span class="stat-label">Deteriorados</span>
                            <span class="stat-value stat-warning">
                                <c:set var="countDeterioro" value="0"/>
                                <c:forEach var="baja" items="${bajas}">
                                    <c:if test="${baja.motivo == 'DETERIORADO'}">
                                        <c:set var="countDeterioro" value="${countDeterioro + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${countDeterioro}
                            </span>
                        </div>
                        <div class="stat-item">
                            <span class="stat-label">Perdidos</span>
                            <span class="stat-value stat-alert">
                                <c:set var="countPerdido" value="0"/>
                                <c:forEach var="baja" items="${bajas}">
                                    <c:if test="${baja.motivo == 'PERDIDO'}">
                                        <c:set var="countPerdido" value="${countPerdido + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${countPerdido}
                            </span>
                        </div>
                        <div class="stat-item">
                            <span class="stat-label">Obsoletos</span>
                            <span class="stat-value">
                                <c:set var="countObsoleto" value="0"/>
                                <c:forEach var="baja" items="${bajas}">
                                    <c:if test="${baja.motivo == 'OBSOLETO'}">
                                        <c:set var="countObsoleto" value="${countObsoleto + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${countObsoleto}
                            </span>
                        </div>
                        <div class="stat-item">
                            <span class="stat-label">Otros Motivos</span>
                            <span class="stat-value">
                                <c:set var="countOtro" value="0"/>
                                <c:forEach var="baja" items="${bajas}">
                                    <c:if test="${baja.motivo == 'OTRO'}">
                                        <c:set var="countOtro" value="${countOtro + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${countOtro}
                            </span>
                        </div>
                    </div>
                </div>
            </c:if>
            
            <div class="form-actions">
                <a href="ControladorSistema?action=listarLibros" class="btn btn-secondary">
                    ← Volver a Libros
                </a>
            </div>
        </main>
    </div>
</body>
</html>