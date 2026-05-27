package com.example.carrito.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Integer requested, Integer available) {
        super("Stock insuficiente. Solicitado: " + requested + ", disponible: " + available);
    }

    public InsufficientStockException() {
        super("Insufficient stock");
    }
}
