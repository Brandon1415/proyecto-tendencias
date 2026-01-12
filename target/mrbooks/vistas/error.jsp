<%-- 
    Document   : error
    Created on : 09/01/2026, 15:44:55
    Author     : ASUS
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - MR Books</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .error-container {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #2c3e50 0%, #3498db 100%);
            padding: 20px;
        }
        
        .error-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            padding: 40px;
            max-width: 600px;
            width: 100%;
            text-align: center;
        }
        
        .error-icon {
            font-size: 5rem;
            margin-bottom: 20px;
        }
        
        .error-title {
            font-size: 2rem;
            color: #2c3e50;
            margin-bottom: 15px;
        }
        
        .error-message {
            color: #7f8c8d;
            font-size: 1.1rem;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        
        .error-details {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            text-align: left;
        }
        
        .error-details h3 {
            color: #e74c3c;
            margin-bottom: 10px;
            font-size: 1.1rem;
        }
        
        .error-details p {
            color: #555;
            font-size: 0.9rem;
            line-height: 1.6;
            word-break: break-word;
        }
        
        .error-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-card">
            <div class="error-icon">⚠️</div>
            
            <h1 class="error-title">¡Ups! Algo salió mal</h1>
            
            <p class="error-message">
                Lo sentimos, ha ocurrido un error inesperado en el sistema.
                Por favor, intenta nuevamente o contacta al administrador.
            </p>
            
            <c:if test="${not empty error}">
                <div class="error-details">
                    <h3>Detalles del Error:</h3>
                    <p>${error}</p>
                </div>
            </c:if>
            
            <c:if test="${not empty exception}">
                <div class="error-details">
                    <h3>Información Técnica:</h3>
                    <p><strong>Tipo:</strong> ${exception.class.name}</p>
                    <p><strong>Mensaje:</strong> ${exception.message}</p>
                </div>
            </c:if>
            
            <div class="error-actions">
                <a href="javascript:history.back()" class="btn btn-secondary">
                    ← Volver Atrás
                </a>
                <a href="ControladorSistema?action=dashboard" class="btn btn-primary">
                    🏠 Ir al Dashboard
                </a>
                <a href="index.jsp" class="btn btn-secondary">
                    🔐 Ir al Login
                </a>
            </div>
            
            <p style="margin-top: 30px; color: #7f8c8d; font-size: 0.85rem;">
                Si el problema persiste, por favor contacta al administrador del sistema.
            </p>
        </div>
    </div>
</body>
</html>