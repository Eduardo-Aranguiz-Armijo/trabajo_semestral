package com.example.orden.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Orden no encontrada con id: " + id);
    }

    public OrderNotFoundException() {
        super("Orden no encontrada");
    }
}
