package com.example.payment.exception;

public class PaymentMethodAlreadyExistsException extends RuntimeException {

    public PaymentMethodAlreadyExistsException(String message) {

        super(message);
    }
}