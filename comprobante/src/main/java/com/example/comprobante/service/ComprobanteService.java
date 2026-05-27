package com.example.comprobante.service;

import com.example.comprobante.dto.ComprobanteRequestDTO;
import com.example.comprobante.dto.ComprobanteResponseDTO;
import com.example.comprobante.exception.ComprobanteAlreadyExistsException;
import com.example.comprobante.exception.ComprobanteGenerationException;
import com.example.comprobante.exception.ComprobanteNotFoundException;
import com.example.comprobante.model.Comprobante;
import com.example.comprobante.repository.ComprobanteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
public class ComprobanteService {

    private final ComprobanteRepository repository;

    public ComprobanteService(ComprobanteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ComprobanteResponseDTO generate(ComprobanteRequestDTO request) {
        if (repository.existsByPaymentId(request.getPaymentId())) {
            throw new ComprobanteAlreadyExistsException(request.getPaymentId());
        }

        if (request.getAmount() == null || request.getAmount() <= 0) {
            throw new ComprobanteGenerationException("El monto del comprobante debe ser mayor a cero");
        }

        Comprobante comprobante = new Comprobante();
        comprobante.setPaymentId(request.getPaymentId());
        comprobante.setOrderId(request.getOrderId());
        comprobante.setClienteId(request.getClienteId());
        comprobante.setAmount(request.getAmount());
        comprobante.setCreatedAt(LocalDateTime.now());
        // Valores temporales: la BD exige NOT NULL antes de tener el id generado
        comprobante.setNumeroComprobante("TEMP-" + UUID.randomUUID());
        comprobante.setContent("Generando comprobante...");

        Comprobante saved = repository.save(comprobante);
        saved.setNumeroComprobante(buildNumeroComprobante(saved.getId()));
        saved.setContent(buildContent(saved));
        saved = repository.save(saved);

        return map(saved);
    }

    public ComprobanteResponseDTO getById(Long id) {
        Comprobante comprobante = repository.findById(id)
                .orElseThrow(() -> new ComprobanteNotFoundException(id));
        return map(comprobante);
    }

    public ComprobanteResponseDTO getByPaymentId(Long paymentId) {
        Comprobante comprobante = repository.findByPaymentId(paymentId)
                .orElseThrow(() -> new ComprobanteNotFoundException());
        return map(comprobante);
    }

    public List<ComprobanteResponseDTO> getByOrderId(Long orderId) {
        return repository.findByOrderIdOrderByCreatedAtDesc(orderId)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<ComprobanteResponseDTO> getByClienteId(Long clienteId) {
        return repository.findByClienteIdOrderByCreatedAtDesc(clienteId)
                .stream()
                .map(this::map)
                .toList();
    }

    private String buildNumeroComprobante(Long id) {
        return "CPB-" + Year.now().getValue() + "-" + String.format("%06d", id);
    }

    private String buildContent(Comprobante comprobante) {
        return """
                ========================================
                       COMPROBANTE DE PAGO
                ========================================
                Número:     %s
                Pago ID:    %d
                Orden ID:   %d
                Cliente ID: %d
                Monto:      $%.2f
                Fecha:      %s
                ========================================
                """.formatted(
                comprobante.getNumeroComprobante(),
                comprobante.getPaymentId(),
                comprobante.getOrderId(),
                comprobante.getClienteId(),
                comprobante.getAmount(),
                comprobante.getCreatedAt()
        );
    }

    private ComprobanteResponseDTO map(Comprobante comprobante) {
        ComprobanteResponseDTO dto = new ComprobanteResponseDTO();
        dto.setId(comprobante.getId());
        dto.setPaymentId(comprobante.getPaymentId());
        dto.setOrderId(comprobante.getOrderId());
        dto.setClienteId(comprobante.getClienteId());
        dto.setNumeroComprobante(comprobante.getNumeroComprobante());
        dto.setAmount(comprobante.getAmount());
        dto.setContent(comprobante.getContent());
        dto.setCreatedAt(comprobante.getCreatedAt());
        return dto;
    }
}
