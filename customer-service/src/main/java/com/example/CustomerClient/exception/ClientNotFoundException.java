package com.example.CustomerClient.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long id) {
        super("Cliente no encontrado con id: " + id);
    }

    public ClientNotFoundException() {
        super("Cliente no encontrado");
    }
}
