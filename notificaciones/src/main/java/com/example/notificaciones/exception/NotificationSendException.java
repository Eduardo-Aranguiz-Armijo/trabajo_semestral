package com.example.notificaciones.exception;

public class NotificationSendException extends RuntimeException {

    public NotificationSendException(String message) {
        super(message);
    }

    public NotificationSendException() {
        super("Error al enviar la notificación");
    }
}
