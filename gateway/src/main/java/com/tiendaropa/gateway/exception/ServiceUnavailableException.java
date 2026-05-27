package com.tiendaropa.gateway.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String serviceName) {
        super("Servicio no disponible: " + serviceName);
    }

    public ServiceUnavailableException() {
        super("Servicio downstream no disponible");
    }
}
