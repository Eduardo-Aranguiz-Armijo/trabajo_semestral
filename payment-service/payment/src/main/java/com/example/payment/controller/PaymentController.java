package com.example.payment.controller;

import com.example.payment.dto.PaymentRequestDTO;
import com.example.payment.dto.PaymentResponseDTO;
import com.example.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(
            PaymentService service
    ) {

        this.service = service;
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
