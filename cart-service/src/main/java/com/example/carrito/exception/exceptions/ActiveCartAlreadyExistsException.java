package com.example.carrito.exception.exceptions;

public class ActiveCartAlreadyExistsException extends RuntimeException {

    public ActiveCartAlreadyExistsException(String message) {

        super(message);
    }
}