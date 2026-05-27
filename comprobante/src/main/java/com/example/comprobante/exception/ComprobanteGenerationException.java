package com.example.comprobante.exception;

public class ComprobanteGenerationException extends RuntimeException {

    public ComprobanteGenerationException(String message) {
        super(message);
    }

    public ComprobanteGenerationException() {
        super("Error al generar el comprobante");
    }
}
