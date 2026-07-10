package com.example.payment.service;

import com.example.payment.client.ClientClient;
import com.example.payment.dto.ClientResponseDTO;
import com.example.payment.dto.PaymentMethodRequestDTO;
import com.example.payment.dto.PaymentMethodResponseDTO;
import com.example.payment.exception.PaymentMethodAlreadyExistsException;
import com.example.payment.model.PaymentMethod;
import com.example.payment.repository.PaymentMethodRepository;
import com.example.payment.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository repository;
    private final JwtService jwtService;
    private final ClientClient clientClient;
    private final HttpServletRequest request;
    public PaymentMethodService(

            PaymentMethodRepository repository,
            JwtService jwtService,
            ClientClient clientClient,
            HttpServletRequest request
    ) {

        this.repository = repository;
        this.jwtService = jwtService;
        this.clientClient = clientClient;
        this.request = request;
    }

    // =========================================
    // CREATE PAYMENT METHOD
    // =========================================

    public PaymentMethodResponseDTO create(PaymentMethodRequestDTO requestDTO
    ) {
        validateCardDuplicate(requestDTO.getCardNumber());
        Long clientId = getCurrentClientId();
        PaymentMethod method = buildPaymentMethod(requestDTO, clientId);
        PaymentMethod saved = repository.save(method);

        return map(saved);
    }

    // =========================================
    // PRIVATE METHODS
    // =========================================

    private void validateCardDuplicate(
            String cardNumber
    ) {
        repository.findByCardNumber(cardNumber).ifPresent(method -> {
                    throw new PaymentMethodAlreadyExistsException("Card already exists");
        });
    }

    private Long getCurrentClientId() {
        String token = request.getHeader("Authorization");
        Long userId = jwtService.extractUserId(token);
        ClientResponseDTO client = clientClient.getByUserId(userId);
        return client.getId();
    }

    private PaymentMethod buildPaymentMethod(
            PaymentMethodRequestDTO requestDTO,
            Long clientId) {

        PaymentMethod method = new PaymentMethod();
        method.setClientId(clientId);
        method.setCardHolder(requestDTO.getCardHolder());
        method.setCardNumber(requestDTO.getCardNumber());
        method.setExpirationDate(requestDTO.getExpirationDate());
        method.setCvv(requestDTO.getCvv());
        method.setCreatedAt(LocalDateTime.now());

        return method;
    }

    // =========================================
    // MAPPER
    // =========================================

    private PaymentMethodResponseDTO map(
            PaymentMethod method
    ) {

        PaymentMethodResponseDTO dto = new PaymentMethodResponseDTO();
        dto.setId(method.getId());
        dto.setClientId(method.getClientId());
        dto.setCardHolder(method.getCardHolder());
        dto.setCardNumber(method.getCardNumber());
        dto.setExpirationDate(method.getExpirationDate());
        dto.setCreatedAt(method.getCreatedAt());

        return dto;
    }
}