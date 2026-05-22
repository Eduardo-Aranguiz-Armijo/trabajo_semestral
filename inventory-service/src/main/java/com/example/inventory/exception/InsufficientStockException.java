package com.example.inventory.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Long productId, Integer requested, Integer available) {
        super("Stock insuficiente para el producto " + productId
                + ". Solicitado: " + requested + ", disponible: " + available);
    }

    public InsufficientStockException() {
        super("Insufficient stock");
    }
}
