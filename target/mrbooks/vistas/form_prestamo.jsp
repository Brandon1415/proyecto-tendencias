<%-- 
    Document   : form_prestamo
    Formulario para registrar nuevo préstamo
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
                <form action="ControladorSistema" method="post" onsubmit="return validarFormulario()">
                    <input type="hidden" name="action" value="registrarPrestamo">
                    
                    <!-- Seleccionar Libro -->
                    <div class="form-group">
                        <label for="libro">Libro: <span class="required">*</span></label>
                        <select id="libro" name="libro" required>
                            <option value="">-- Selecciona un libro --</option>
                            <c:forEach var="libro" items="${libros}">
                                <c:if test="${libro.copiasDisponibles > 0}">
                                    <option value="${libro.idLibro}">
                                        ${libro.titulo} - ${libro.autor} (Disponibles: ${libro.copiasDisponibles})
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <small style="color: #7f8c8d; font-size: 0.85rem;">
                            Solo se muestran libros con copias disponibles
                        </small>
                    </div>
                    
                    <!-- Seleccionar Lector -->
                    <div class="form-group">
                        <label for="lector">Lector: <span class="required">*</span></label>
                        <select id="lector" name="lector" required>
                            <option value="">-- Selecciona un lector --</option>
                            <c:forEach var="lector" items="${lectores}">
                                <option value="${lector.idLector}">
                                    ${lector.nombre} ${lector.apellido} - ${lector.cedula}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <!-- Información del Préstamo -->
                    <div class="alert alert-success">
                        <span>ℹ️</span>
                        <div>
                            <strong>Información del Préstamo:</strong><br>
                            • El préstamo se registrará con la fecha actual<br>
                            • La fecha de devolución será en 15 días<br>
                            • El lector recibirá una notificación de las fechas
                        </div>
                    </div>
                    
                    <!-- Botones de acción -->
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
    
    <script>
        // ✅ Validación antes de enviar el formulario
        function validarFormulario() {
            const libro = document.getElementById('libro').value;
            const lector = document.getElementById('lector').value;
            
            // Verificar que no estén vacíos
            if (!libro || libro === '') {
                alert('⚠️ Por favor selecciona un libro');
                return false;
            }
            
            if (!lector || lector === '') {
                alert('⚠️ Por favor selecciona un lector');
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>