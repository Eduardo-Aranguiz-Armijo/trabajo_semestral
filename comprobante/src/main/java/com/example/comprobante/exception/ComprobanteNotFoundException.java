package com.example.comprobante.exception;

public class ComprobanteNotFoundException extends RuntimeException {

    public ComprobanteNotFoundException(Long id) {
        super("Comprobante no encontrado con id: " + id);
    }

    public ComprobanteNotFoundException() {
        super("Comprobante no encontrado");
    }
}
