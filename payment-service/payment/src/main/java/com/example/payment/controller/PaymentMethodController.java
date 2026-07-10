package com.example.payment.controller;

import com.example.payment.dto.PaymentMethodRequestDTO;
import com.example.payment.dto.PaymentMethodResponseDTO;
import com.example.payment.service.PaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "metodo-pago", description = "Gestión de métodos de pago")
@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService service;

    public PaymentMethodController(PaymentMethodService service) {
        this.service = service;
    }

    // =========================================
    // CREATE PAYMENT METHOD
    // =========================================

    @Operation(summary = "Crear método de pago", description = "Registra un nuevo método de pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Método de pago creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentMethodResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del método de pago inválidos")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<PaymentMethodResponseDTO> create(
            @Valid @RequestBody PaymentMethodRequestDTO request) {
        PaymentMethodResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}