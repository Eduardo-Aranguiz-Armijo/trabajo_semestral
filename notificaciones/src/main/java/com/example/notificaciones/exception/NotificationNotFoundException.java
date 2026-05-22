package com.example.notificaciones.exception;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super("Notificación no encontrada con id: " + id);
    }

    public NotificationNotFoundException() {
        super("Notificación no encontrada");
    }
}
