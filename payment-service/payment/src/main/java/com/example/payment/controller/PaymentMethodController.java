package com.example.payment.controller;

import com.example.payment.dto.PaymentMethodRequestDTO;
import com.example.payment.dto.PaymentMethodResponseDTO;
import com.example.payment.service.PaymentMethodService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {
    private final PaymentMethodService service;

    public PaymentMethodController(
            PaymentMethodService service
    ) {

        this.service = service;
    }

    @PostMapping
    public ResponseEntity
            <PaymentMethodResponseDTO>
    create(
            @Valid
            @RequestBody
            PaymentMethodRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.create(request)
        );
    }
}
