package com.example.carrito.exception.exceptions;

public class InvalidCartStateException extends RuntimeException{
    public InvalidCartStateException(String message) {

        super(message);
    }
}
