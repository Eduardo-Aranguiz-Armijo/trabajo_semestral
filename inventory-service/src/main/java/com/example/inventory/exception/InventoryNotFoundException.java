package com.example.inventory.exception;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(Long productId) {
        super("Inventario no encontrado para el producto: " + productId);
    }

    public InventoryNotFoundException() {
        super("Inventario no encontrado");
    }
}
