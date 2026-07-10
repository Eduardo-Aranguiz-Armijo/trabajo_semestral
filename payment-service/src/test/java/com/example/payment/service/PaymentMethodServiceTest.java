package com.example.payment.service;

import com.example.payment.client.ClientClient;
import com.example.payment.dto.ClientResponseDTO;
import com.example.payment.dto.PaymentMethodRequestDTO;
import com.example.payment.dto.PaymentMethodResponseDTO;
import com.example.payment.model.PaymentMethod;
import com.example.payment.repository.PaymentMethodRepository;
import com.example.payment.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentMethodServiceTest {

    @Mock
    private PaymentMethodRepository repository;

    @Mock
    private JwtService jwtService;

    @Mock
    private ClientClient clienteClient;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PaymentMethodService service;

    @Test
    void debeCrearMetodoPago() {

        PaymentMethodRequestDTO requestDTO =
                new PaymentMethodRequestDTO();

        requestDTO.setCardHolder("Juan Perez");
        requestDTO.setCardNumber("123456789");
        requestDTO.setExpirationDate("12/30");
        requestDTO.setCvv("123");

        ClientResponseDTO cliente =
                new ClientResponseDTO();

        cliente.setId(10L);

        PaymentMethod saved =
                new PaymentMethod();

        saved.setId(1L);
        saved.setClienteId(10L);
        saved.setCardHolder("Juan Perez");
        saved.setCardNumber("123456789");
        saved.setExpirationDate("12/30");
        saved.setCreatedAt(LocalDateTime.now());

        when(repository.findByCardNumber("123456789"))
                .thenReturn(Optional.empty());

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer token");

        when(jwtService.extractUserId("Bearer token"))
                .thenReturn(5L);

        when(clienteClient.getByUserId(5L))
                .thenReturn(cliente);

        when(repository.save(any(PaymentMethod.class)))
                .thenReturn(saved);

        PaymentMethodResponseDTO result =
                service.create(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10L, result.getClienteId());
        assertEquals("Juan Perez", result.getCardHolder());
        assertEquals("123456789", result.getCardNumber());

        verify(repository)
                .findByCardNumber("123456789");

        verify(repository)
                .save(any(PaymentMethod.class));
    }

    @Test
    void debeLanzarExcepcionSiTarjetaExiste() {

        PaymentMethodRequestDTO requestDTO =
                new PaymentMethodRequestDTO();

        requestDTO.setCardNumber("123456789");

        PaymentMethod existing =
                new PaymentMethod();

        when(repository.findByCardNumber("123456789"))
                .thenReturn(Optional.of(existing));

        assertThrows(
                RuntimeException.class,
                () -> service.create(requestDTO)
        );

        verify(repository)
                .findByCardNumber("123456789");

        verify(repository, never())
                .save(any());
    }
}