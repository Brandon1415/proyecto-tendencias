<%-- 
    Document   : index
    Created on : 05/01/2026, 20:22:43
    Author     : ASUS
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body class="login-page">
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <!-- LOGO -->
                <img src="img/logoOf.png" alt="MR Books Logo" class="login-logo">
                
                <h1>MR Books</h1>
                <p>Sistema de Gestión Bibliotecaria</p>
            </div>
            
            <div class="login-form">
                <% if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-error">
                        <span>⚠️</span>
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="autenticar">
                    
                    <div class="form-group">
                        <label for="email">Correo Electrónico</label>
                        <input type="email" id="email" name="email" 
                               placeholder="admin@mrbooks.com" 
                               required autofocus>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Contraseña</label>
                        <input type="password" id="password" name="password" 
                               placeholder="••••••••" 
                               required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-block">
                        🔐 Iniciar Sesión
                    </button>
                </form>
            </div>
            
            <div class="login-footer">
                <div class="info-box">
                    <strong>📌 Credenciales de prueba:</strong><br>
                    <strong>Admin:</strong> admin@mrbooks.com / admin123<br>
                    <strong>Empleado:</strong> juan.perez@mrbooks.com / emp123
                </div>
                <p class="copyright">© 2025 MR Books - La Casa de las Palabras</p>
            </div>
        </div>
    </div>
</body>
</html>