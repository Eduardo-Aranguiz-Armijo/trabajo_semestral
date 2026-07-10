package com.example.payment.controller;

import com.example.payment.dto.PaymentRequestDTO;
import com.example.payment.dto.PaymentResponseDTO;
import com.example.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment", description = "Endpoints para procesar pagos")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(
            PaymentService service
    ) {

        this.service = service;
    }

    @Operation(summary = "Procesar pago", description = "Procesa un nuevo pago para una orden")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity
            <PaymentResponseDTO>
    create(
            @Valid
            @RequestBody
            PaymentRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.create(request)
        );
    }

    @Operation(summary = "Obtener pago por ID", description = "Obtiene los detalles de un pago específico")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
