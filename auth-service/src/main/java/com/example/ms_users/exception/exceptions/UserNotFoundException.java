package com.example.ms_users.exception.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {

        super(message);
    }
}