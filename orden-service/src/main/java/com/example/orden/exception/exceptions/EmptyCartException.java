package com.example.orden.exception.exceptions;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {

        super(message);
    }
}