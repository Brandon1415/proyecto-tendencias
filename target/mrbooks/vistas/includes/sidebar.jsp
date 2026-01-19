<%-- 
    Document   : sidebar
    Created on : 09/01/2026, 13:15:42
    Author     : ASUS
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="sidebar">
    <div class="sidebar-header">
        <!-- LOGO -->
        <img src="${pageContext.request.contextPath}/img/logoOf.png" alt="MR Books Logo" class="sidebar-logo">
         
        <div class="user-info">
            <p class="user-name">👤 ${nombreUsuario}</p>
            <span class="user-role">${rol}</span>
        </div>
    </div>
    
    <nav class="sidebar-menu">
        <a href="${pageContext.request.contextPath}/ControladorSistema?action=dashboard" class="menu-item ${param.page == 'dashboard' ? 'active' : ''}">
            <span>📊</span> <span>Dashboard</span>
        </a>
        
        <c:if test="${rol == 'ADMINISTRADOR'}">
            <a href="${pageContext.request.contextPath}/ControladorSistema?action=listarUsuarios" class="menu-item ${param.page == 'usuarios' ? 'active' : ''}">
                <span>👤</span> <span>Usuarios</span>
            </a>
        </c:if>
        
        <a href="${pageContext.request.contextPath}/ControladorSistema?action=listarLectores" class="menu-item ${param.page == 'lectores' ? 'active' : ''}">
            <span>👥</span> <span>Lectores</span>
        </a>
                
        <a href="${pageContext.request.contextPath}/ControladorSistema?action=listarLibros" class="menu-item ${param.page == 'libros' ? 'active' : ''}">
            <span>📚</span> <span>Libros</span>
        </a>
        
        <a href="${pageContext.request.contextPath}/ControladorSistema?action=listarPrestamos" class="menu-item ${param.page == 'prestamos' ? 'active' : ''}">
            <span>🔄</span> <span>Préstamos</span>
        </a>
        
        <a href="${pageContext.request.contextPath}/ControladorSistema?action=listarReservas" class="menu-item ${param.page == 'reservas' ? 'active' : ''}">
            <span>📅</span> <span>Reservas</span>
        </a>
        
        <a href="${pageContext.request.contextPath}/ControladorSistema?action=reportes" class="menu-item ${param.page == 'reportes' ? 'active' : ''}">
            <span>📈</span> <span>Reportes</span>
        </a>
        
        <a href="${pageContext.request.contextPath}/ControladorSistema?action=logout" class="menu-item menu-logout">
            <span>🚪</span> <span>Cerrar Sesión</span>
        </a>
    </nav>
</aside>