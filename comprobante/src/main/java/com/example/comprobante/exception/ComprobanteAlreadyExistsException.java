package com.example.comprobante.exception;

public class ComprobanteAlreadyExistsException extends RuntimeException {

    public ComprobanteAlreadyExistsException(Long paymentId) {
        super("Ya existe un comprobante para el pago: " + paymentId);
    }
}
