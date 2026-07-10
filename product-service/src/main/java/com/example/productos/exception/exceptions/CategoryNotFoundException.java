package com.example.productos.exception.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {

        super(message);
    }
}