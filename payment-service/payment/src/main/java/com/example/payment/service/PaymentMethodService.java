package com.example.payment.service;

import com.example.payment.client.ClientClient;
import com.example.payment.dto.ClientResponseDTO;
import com.example.payment.dto.PaymentMethodRequestDTO;
import com.example.payment.dto.PaymentMethodResponseDTO;
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

    private final ClientClient clienteClient;

    private final HttpServletRequest request;

    public PaymentMethodService(
            PaymentMethodRepository repository,
            JwtService jwtService, ClientClient clienteClient,
            HttpServletRequest request
    ) {

        this.repository = repository;

        this.jwtService = jwtService;
        this.clienteClient = clienteClient;


        this.request = request;
    }

    public PaymentMethodResponseDTO create(
            PaymentMethodRequestDTO requestDTO
    ) {

        repository.findByCardNumber(
                requestDTO.getCardNumber()
        ).ifPresent(p -> {

            throw new RuntimeException(
                    "Card already exists"
            );
        });

        String token =
                request.getHeader("Authorization");

        Long userId =
                jwtService.extractUserId(token);

        ClientResponseDTO cliente =
                clienteClient.getByUserId(userId);

        PaymentMethod method =
                new PaymentMethod();

        method.setClienteId(
                cliente.getId()
        );

        method.setCardHolder(
                requestDTO.getCardHolder()
        );

        method.setCardNumber(
                requestDTO.getCardNumber()
        );

        method.setExpirationDate(
                requestDTO.getExpirationDate()
        );

        method.setCvv(
                requestDTO.getCvv()
        );

        method.setCreatedAt(
                LocalDateTime.now()
        );

        PaymentMethod saved =
                repository.save(method);

        return map(saved);
    }

    private PaymentMethodResponseDTO map(
            PaymentMethod method
    ) {

        PaymentMethodResponseDTO dto =
                new PaymentMethodResponseDTO();

        dto.setId(method.getId());

        dto.setClienteId(
                method.getClienteId()
        );

        dto.setCardHolder(
                method.getCardHolder()
        );

        dto.setCardNumber(
                method.getCardNumber()
        );

        dto.setExpirationDate(
                method.getExpirationDate()
        );

        dto.setCreatedAt(
                method.getCreatedAt()
        );

        return dto;
    }
}
