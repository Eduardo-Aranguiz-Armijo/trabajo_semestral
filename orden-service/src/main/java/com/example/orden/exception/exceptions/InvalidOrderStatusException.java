package com.example.orden.exception.exceptions;

public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException(String message) {

        super(message);
    }
}