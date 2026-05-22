package com.example.payment.exception;

public class PaymentMethodNotFoundException extends RuntimeException {

    public PaymentMethodNotFoundException(Long id) {
        super("Payment method not found with id: " + id);
    }

    public PaymentMethodNotFoundException() {
        super("Payment method not found");
    }
}
