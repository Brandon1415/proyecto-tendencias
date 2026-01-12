<%-- 
    Document   : dashboard
    Created on : 09/01/2026, 12:46:19
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="dashboard-container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h2>📚 MR Books</h2>
                <div class="user-info">
                    <p class="user-name">${nombreUsuario}</p>
                    <span class="user-role">${rol}</span>
                </div>
            </div>
            
            <nav class="sidebar-menu">
                <a href="ControladorSistema?action=dashboard" class="menu-item active">
                    <span>📊</span> Dashboard
                </a>
                <c:if test="${rol == 'ADMINISTRADOR'}">
                    <a href="ControladorSistema?action=listarUsuarios" class="menu-item">
                        <span>👤</span> Usuarios
                    </a>
                </c:if>
                <a href="ControladorSistema?action=listarLectores" class="menu-item">
                    <span>👥</span> Lectores
                </a>
                <a href="ControladorSistema?action=listarLibros" class="menu-item">
                    <span>📖</span> Libros
                </a>
                <a href="ControladorSistema?action=listarPrestamos" class="menu-item">
                    <span>🔄</span> Préstamos
                </a>
                <a href="ControladorSistema?action=listarReservas" class="menu-item">
                    <span>📅</span> Reservas
                </a>
                <a href="ControladorSistema?action=reportes" class="menu-item">
                    <span>📈</span> Reportes
                </a>
                <a href="ControladorSistema?action=logout" class="menu-item menu-logout">
                    <span>🚪</span> Cerrar Sesión
                </a>
            </nav>
        </aside>
        
        <!-- Main Content -->
        <main class="main-content">
            <div class="content-header">
                <h1>Dashboard</h1>
                <p class="breadcrumb">Inicio / Dashboard</p>
            </div>
            
            <!-- KPIs Grid -->
            <div class="kpis-grid">
                <div class="kpi-card">
                    <div class="kpi-icon">📚</div>
                    <div class="kpi-info">
                        <h3>${kpis.total_libros}</h3>
                        <p>Total Libros</p>
                    </div>
                </div>
                
                <div class="kpi-card">
                    <div class="kpi-icon">👥</div>
                    <div class="kpi-info">
                        <h3>${kpis.total_lectores}</h3>
                        <p>Lectores Activos</p>
                    </div>
                </div>
                
                <div class="kpi-card">
                    <div class="kpi-icon">🔄</div>
                    <div class="kpi-info">
                        <h3>${kpis.prestamos_activos}</h3>
                        <p>Préstamos Activos</p>
                    </div>
                </div>
                
                <div class="kpi-card kpi-warning">
                    <div class="kpi-icon">⚠️</div>
                    <div class="kpi-info">
                        <h3>${kpis.prestamos_atrasados}</h3>
                        <p>Préstamos Atrasados</p>
                    </div>
                </div>
                
                <div class="kpi-card">
                    <div class="kpi-icon">📅</div>
                    <div class="kpi-info">
                        <h3>${kpis.reservas_activas}</h3>
                        <p>Reservas Activas</p>
                    </div>
                </div>
                
                <div class="kpi-card">
                    <div class="kpi-icon">📦</div>
                    <div class="kpi-info">
                        <h3>${kpis.copias_disponibles}</h3>
                        <p>Copias Disponibles</p>
                    </div>
                </div>
                
                <c:if test="${rol == 'ADMINISTRADOR'}">
                    <div class="kpi-card">
                        <div class="kpi-icon">👤</div>
                        <div class="kpi-info">
                            <h3>${kpis.total_usuarios}</h3>
                            <p>Usuarios Sistema</p>
                        </div>
                    </div>
                </c:if>
                
                <div class="kpi-card">
                    <div class="kpi-icon">🏷️</div>
                    <div class="kpi-info">
                        <h3>${kpis.total_categorias}</h3>
                        <p>Categorías</p>
                    </div>
                </div>
            </div>
            
            <!-- Estadísticas -->
            <div class="stats-section">
                <h2>📊 Estadísticas Generales</h2>
                <div class="stats-grid">
                    <div class="stat-item">
                        <span class="stat-label">Total Préstamos Realizados</span>
                        <span class="stat-value">${stats.total_prestamos_realizados}</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Total Bajas Registradas</span>
                        <span class="stat-value">${stats.total_bajas}</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Total Reservas</span>
                        <span class="stat-value">${stats.total_reservas_realizadas}</span>
                    </div>
                    <c:if test="${rol == 'ADMINISTRADOR'}">
                        <div class="stat-item">
                            <span class="stat-label">Usuarios Bloqueados</span>
                            <span class="stat-value stat-alert">${stats.usuarios_bloqueados}</span>
                        </div>
                    </c:if>
                </div>
            </div>
            
            <!-- Accesos Rápidos -->
            <div class="quick-actions">
                <h2>⚡ Accesos Rápidos</h2>
                <div class="actions-grid">
                    <a href="ControladorSistema?action=nuevoPrestamo" class="action-card">
                        <span>➕</span>
                        <p>Nuevo Préstamo</p>
                    </a>
                    <a href="ControladorSistema?action=nuevoLector" class="action-card">
                        <span>👤</span>
                        <p>Nuevo Lector</p>
                    </a>
                    <a href="ControladorSistema?action=nuevoLibro" class="action-card">
                        <span>📚</span>
                        <p>Nuevo Libro</p>
                    </a>
                    <a href="ControladorSistema?action=nuevaReserva" class="action-card">
                        <span>📅</span>
                        <p>Nueva Reserva</p>
                    </a>
                </div>
            </div>
        </main>
    </div>
</body>
</html>