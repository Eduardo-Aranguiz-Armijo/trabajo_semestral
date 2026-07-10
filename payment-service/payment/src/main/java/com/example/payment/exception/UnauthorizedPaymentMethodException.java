package com.example.payment.exception;

public class UnauthorizedPaymentMethodException extends RuntimeException {

    public UnauthorizedPaymentMethodException(String message) {

        super(message);
    }
}
