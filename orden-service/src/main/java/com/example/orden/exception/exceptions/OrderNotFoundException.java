package com.example.orden.exception.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {

        super(message);
    }
}