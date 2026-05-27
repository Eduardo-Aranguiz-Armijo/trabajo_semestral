package com.example.comprobante.controller;

import com.example.comprobante.dto.ComprobanteRequestDTO;
import com.example.comprobante.dto.ComprobanteResponseDTO;
import com.example.comprobante.service.ComprobanteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comprobantes")
public class ComprobanteController {

    private final ComprobanteService service;

    public ComprobanteController(ComprobanteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ComprobanteResponseDTO> generate(
            @Valid @RequestBody ComprobanteRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.generate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<ComprobanteResponseDTO> getByPaymentId(
            @PathVariable Long paymentId
    ) {
        return ResponseEntity.ok(service.getByPaymentId(paymentId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ComprobanteResponseDTO>> getByOrderId(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(service.getByOrderId(orderId));
    }

    @GetMapping("/customer/{clienteId}")
    public ResponseEntity<List<ComprobanteResponseDTO>> getByClienteId(
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(service.getByClienteId(clienteId));
    }
}
