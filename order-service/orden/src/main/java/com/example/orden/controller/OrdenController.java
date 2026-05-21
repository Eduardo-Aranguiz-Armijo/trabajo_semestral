package com.example.orden.controller;

import com.example.orden.dto.OrderResponseDTO;
import com.example.orden.service.OrdenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdenController {
    private final OrdenService service;

    public OrdenController(
            OrdenService service
    ) {

        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO>
    createOrder() {

        return ResponseEntity.ok(
                service.createOrder()
        );
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO>
    updateStatus(
            @PathVariable Long id,
            @RequestParam String estado
    ) {

        return ResponseEntity.ok(
                service.updateStatus(
                        id,
                        estado
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO>
    getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }
}
