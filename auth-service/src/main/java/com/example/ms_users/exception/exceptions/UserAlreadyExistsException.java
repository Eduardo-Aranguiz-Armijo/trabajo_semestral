package com.example.ms_users.exception.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {

        super(message);
    }
}
