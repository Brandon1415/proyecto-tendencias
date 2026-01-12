<%-- 
    Document   : index
    Created on : 05/01/2026, 20:22:43
    Author     : ASUS
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Sistema MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="login-page">
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <h1>📚 MR Books</h1>
                <p>Sistema de Gestión Bibliotecaria</p>
            </div>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <span>⚠️</span>
                    <span>${error}</span>
                </div>
            </c:if>
            
            <form action="ControladorSistema" method="post" class="login-form">
                <input type="hidden" name="action" value="autenticar">
                
                <div class="form-group">
                    <label for="email">Correo Electrónico</label>
                    <input type="email" id="email" name="email" placeholder="ejemplo@mrbooks.com" required autofocus>
                </div>
                
                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password" placeholder="••••••••" required>
                </div>
                
                <button type="submit" class="btn btn-primary btn-block">Iniciar Sesión</button>
            </form>
            
            <div class="login-footer">
                <div class="info-box">
                    <strong>Credenciales de prueba:</strong><br>
                    <small>Admin: admin@mrbooks.com / admin123</small><br>
                    <small>Empleado: juan.perez@mrbooks.com / emp123</small>
                </div>
                <p class="copyright">&copy; 2025 MR Books</p>
            </div>
        </div>
    </div>
</body>
</html>