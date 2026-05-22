package com.example.ms_users.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Usuario no encontrado: " + username);
    }

    public UserNotFoundException() {
        super("Usuario no encontrado");
    }
}
