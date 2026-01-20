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
                <img src="img/logoOf.png" alt="MR Books Logo" class="login-logo">
                <h1>MR Books</h1>
                <p>Sistema de Gestión Bibliotecaria</p>
            </div>
            
            <div class="login-form">
                <%-- Mostrar mensaje de bloqueo --%>
                <% if(request.getAttribute("bloqueado") != null && (Boolean)request.getAttribute("bloqueado")) { %>
                    <div class="alert alert-error cuenta-bloqueada">
                        <span>⏰</span>
                        <%= request.getAttribute("error") %>
                        <% if(request.getAttribute("tiempoRestante") != null) { 
                            int tiempo = (Integer)request.getAttribute("tiempoRestante");
                            int minutos = tiempo / 60;
                            int segundos = tiempo % 60;
                        %>
                            <div class="bloqueo-tiempo">
                                Tiempo restante: <%= minutos %>:<%= String.format("%02d", segundos) %>
                            </div>
                        <% } %>
                    </div>
                <% } else if(request.getAttribute("error") != null) { %>
                    <div class="alert alert-error">
                        <span>⚠️</span>
                        <%= request.getAttribute("error") %>
                        <% if(request.getAttribute("intentosRestantes") != null) { %>
                            <div class="intentos-restantes">
                                Intentos restantes: <%= request.getAttribute("intentosRestantes") %>
                            </div>
                        <% } %>
                    </div>
                <% } %>
                
                <form action="ControladorSistema" method="post">
                    <input type="hidden" name="action" value="autenticar">
                    
                    <div class="form-group">
                        <label for="email">Correo Electrónico</label>
                        <input type="email" id="email" name="email" 
                               value="<%= request.getAttribute("email") != null ? 
                                       request.getAttribute("email") : "" %>"
                               placeholder="admin@mrbooks.com" 
                               required autofocus
                               <%= request.getAttribute("bloqueado") != null && 
                                   (Boolean)request.getAttribute("bloqueado") ? "readonly" : "" %>>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Contraseña</label>
                        <input type="password" id="password" name="password" 
                               placeholder="••••••••" 
                               required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-block"
                        <%= request.getAttribute("bloqueado") != null && 
                            (Boolean)request.getAttribute("bloqueado") ? "disabled" : "" %>>
                        <%= request.getAttribute("bloqueado") != null && 
                            (Boolean)request.getAttribute("bloqueado") ? 
                            "⏳ Cuenta Bloqueada" : "🔐 Iniciar Sesión" %>
                    </button>
                </form>
            </div>
            
            <div class="login-footer">
                <p class="copyright">© 2025 MR Books - La Casa de las Palabras</p>
            </div>
        </div>
    </div>
    
    <%-- Script para actualizar contador de bloqueo --%>
    <% if(request.getAttribute("bloqueado") != null && (Boolean)request.getAttribute("bloqueado") 
          && request.getAttribute("tiempoRestante") != null) { 
        int tiempoInicial = (Integer)request.getAttribute("tiempoRestante");
    %>
    <script>
        let tiempoRestante = <%= tiempoInicial %>;
        
        function actualizarContador() {
            if (tiempoRestante <= 0) {
                location.reload(); // Recargar cuando termine el bloqueo
                return;
            }
            
            tiempoRestante--;
            const minutos = Math.floor(tiempoRestante / 60);
            const segundos = tiempoRestante % 60;
            
            const contador = document.querySelector('.bloqueo-tiempo');
            if (contador) {
                contador.textContent = 'Tiempo restante: ' + minutos + ':' + 
                    (segundos < 10 ? '0' : '') + segundos;
            }
            
            // Si ya no está bloqueado, habilitar formulario
            if (tiempoRestante <= 0) {
                document.getElementById('email').removeAttribute('readonly');
                document.querySelector('button[type="submit"]').disabled = false;
                document.querySelector('button[type="submit"]').textContent = '🔐 Iniciar Sesión';
            }
        }
        
        // Actualizar cada segundo
        setInterval(actualizarContador, 1000);
    </script>
    <% } %>
</body>
</html>