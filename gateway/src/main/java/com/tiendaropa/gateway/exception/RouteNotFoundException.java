package com.tiendaropa.gateway.exception;

public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException(String path) {
        super("Ruta no encontrada en el gateway: " + path);
    }
}
