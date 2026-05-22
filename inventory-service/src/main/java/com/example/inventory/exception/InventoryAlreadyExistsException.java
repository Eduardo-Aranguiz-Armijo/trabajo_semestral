package com.example.inventory.exception;

public class InventoryAlreadyExistsException extends RuntimeException {

    public InventoryAlreadyExistsException(Long productId) {
        super("Inventory already exists for product: " + productId);
    }

    public InventoryAlreadyExistsException() {
        super("Inventory already exists");
    }
}
