package com.example.payment.exception;

public class CardAlreadyExistsException extends RuntimeException {

    public CardAlreadyExistsException(String cardNumber) {
        super("Card already exists: " + cardNumber);
    }

    public CardAlreadyExistsException() {
        super("Card already exists");
    }
}
