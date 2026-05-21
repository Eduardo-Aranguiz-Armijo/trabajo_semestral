package com.example.productos.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Producto no encontrado con id: " + id);
    }

    public ProductNotFoundException() {
        super("Producto no encontrado");
    }
}
