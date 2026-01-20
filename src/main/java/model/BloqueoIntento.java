/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar bloqueos por intentos fallidos de login
 * Almacena en memoria (podría extenderse a Redis en producción)
 */
public class BloqueoIntento {
    
    private static BloqueoIntento instance;
    private Map<String, IntentosUsuario> intentosPorEmail;
    
    // Configuración
    private static final int MAX_INTENTOS = 3;
    private static final long TIEMPO_BLOQUEO_MS = 5 * 60 * 1000; // 5 minutos
    
    private BloqueoIntento() {
        intentosPorEmail = new HashMap<>();
    }
    
    public static synchronized BloqueoIntento getInstance() {
        if (instance == null) {
            instance = new BloqueoIntento();
        }
        return instance;
    }
    
    /**
     * Registra un intento fallido
     * @param email Email del usuario
     * @return true si el usuario está bloqueado, false si aún tiene intentos
     */
    public boolean registrarIntentoFallido(String email) {
        IntentosUsuario intentos = intentosPorEmail.get(email);
        
        if (intentos == null) {
            intentos = new IntentosUsuario();
            intentosPorEmail.put(email, intentos);
        }
        
        // Si el bloqueo ya expiró, reiniciar contador
        if (intentos.estaBloqueado() && intentos.tiempoRestanteBloqueo() <= 0) {
            intentos.reiniciar();
        }
        
        // Incrementar intentos
        intentos.incrementarIntentos();
        
        // Bloquear si superó el límite
        if (intentos.getIntentosFallidos() >= MAX_INTENTOS) {
            intentos.setBloqueadoHasta(System.currentTimeMillis() + TIEMPO_BLOQUEO_MS);
            return true;
        }
        
        return false;
    }
    
    /**
     * Verifica si un usuario está bloqueado
     */
    public boolean estaBloqueado(String email) {
        IntentosUsuario intentos = intentosPorEmail.get(email);
        if (intentos == null) return false;
        
        // Si el bloqueo expiró, limpiar
        if (intentos.estaBloqueado() && intentos.tiempoRestanteBloqueo() <= 0) {
            intentos.reiniciar();
            return false;
        }
        
        return intentos.estaBloqueado();
    }
    
    /**
     * Obtiene tiempo restante de bloqueo en segundos
     */
    public int getTiempoRestanteBloqueo(String email) {
        IntentosUsuario intentos = intentosPorEmail.get(email);
        if (intentos == null) return 0;
        return intentos.tiempoRestanteBloqueo();
    }
    
    /**
     * Obtiene intentos fallidos restantes antes del bloqueo
     */
    public int getIntentosRestantes(String email) {
        IntentosUsuario intentos = intentosPorEmail.get(email);
        if (intentos == null) return MAX_INTENTOS;
        
        // Si está bloqueado, mostrar 0
        if (intentos.estaBloqueado()) return 0;
        
        return MAX_INTENTOS - intentos.getIntentosFallidos();
    }
    
    /**
     * Resetea los intentos cuando el login es exitoso
     */
    public void resetearIntentos(String email) {
        IntentosUsuario intentos = intentosPorEmail.get(email);
        if (intentos != null) {
            intentos.reiniciar();
        }
    }
    
    /**
     * Clase interna para manejar los intentos por usuario
     */
    private static class IntentosUsuario {
        private int intentosFallidos;
        private long bloqueadoHasta; // timestamp en ms, 0 si no está bloqueado
        
        public IntentosUsuario() {
            this.intentosFallidos = 0;
            this.bloqueadoHasta = 0;
        }
        
        public void incrementarIntentos() {
            this.intentosFallidos++;
        }
        
        public int getIntentosFallidos() {
            return intentosFallidos;
        }
        
        public boolean estaBloqueado() {
            return bloqueadoHasta > 0;
        }
        
        public void setBloqueadoHasta(long timestamp) {
            this.bloqueadoHasta = timestamp;
        }
        
        public int tiempoRestanteBloqueo() {
            if (!estaBloqueado()) return 0;
            long tiempoRestante = bloqueadoHasta - System.currentTimeMillis();
            return (int) Math.max(0, tiempoRestante / 1000); // en segundos
        }
        
        public void reiniciar() {
            this.intentosFallidos = 0;
            this.bloqueadoHasta = 0;
        }
    }
}