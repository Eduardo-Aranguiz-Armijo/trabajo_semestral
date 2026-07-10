package com.example.carrito.exception.exceptions;

public class UnauthorizedCartAccessException extends RuntimeException{
    public UnauthorizedCartAccessException(String message) {

        super(message);
    }
}
