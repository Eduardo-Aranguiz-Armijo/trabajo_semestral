package com.example.payment.exception;

public class PaymentMethodOwnershipException extends RuntimeException {

    public PaymentMethodOwnershipException() {
        super("Payment method does not belong to customer");
    }
}
