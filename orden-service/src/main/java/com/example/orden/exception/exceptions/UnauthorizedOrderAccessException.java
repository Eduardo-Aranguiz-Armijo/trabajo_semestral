package com.example.orden.exception.exceptions;

public class UnauthorizedOrderAccessException extends RuntimeException {

    public UnauthorizedOrderAccessException(String message) {

        super(message);
    }
}