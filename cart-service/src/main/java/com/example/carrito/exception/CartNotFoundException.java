package com.example.carrito.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(Long id) {
        super("Carrito no encontrado con id: " + id);
    }

    public CartNotFoundException() {
        super("Carrito no encontrado");
    }
}
