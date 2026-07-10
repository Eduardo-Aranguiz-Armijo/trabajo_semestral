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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/payment-methods")
@Tag(name = "Payment Methods", description = "Endpoints para la gestión de métodos de pago")
public class PaymentMethodController {
    private final PaymentMethodService service;

    public PaymentMethodController(
            PaymentMethodService service
    ) {

        this.service = service;
    }

    @Operation(summary = "Crear método de pago", description = "Registra un nuevo método de pago en el sistema")
    @SecurityRequirement(name = "bearerAuth")
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
