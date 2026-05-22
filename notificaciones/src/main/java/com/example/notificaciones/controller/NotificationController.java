package com.example.notificaciones.controller;

import com.example.notificaciones.dto.NotificationRequestDTO;
import com.example.notificaciones.dto.NotificationResponseDTO;
import com.example.notificaciones.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(
            @Valid @RequestBody NotificationRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createAndSend(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/customer/{clienteId}")
    public ResponseEntity<List<NotificationResponseDTO>> getByClienteId(
            @PathVariable Long clienteId
    ) {
        return ResponseEntity.ok(service.getByClienteId(clienteId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationResponseDTO>> getByOrderId(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(service.getByOrderId(orderId));
    }

    @PostMapping("/{id}/resend")
    public ResponseEntity<NotificationResponseDTO> resend(@PathVariable Long id) {
        return ResponseEntity.ok(service.resend(id));
    }
}
