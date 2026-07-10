package com.example.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // =========================================
    // VALIDATIONS
    // =========================================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    // =========================================
    // PAYMENT METHOD
    // =========================================

    @ExceptionHandler(PaymentMethodAlreadyExistsException.class)
    public ResponseEntity<String> handleCardExists(PaymentMethodAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(PaymentMethodNotFoundException.class)
    public ResponseEntity<String> handleMethodNotFound(PaymentMethodNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedPaymentMethodException.class)
    public ResponseEntity<String> handleUnauthorizedMethod(UnauthorizedPaymentMethodException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // =========================================
    // PAYMENT
    // =========================================

    @ExceptionHandler(PaymentAlreadyProcessedException.class)
    public ResponseEntity<String> handlePaymentAlreadyProcessed(PaymentAlreadyProcessedException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());}
}