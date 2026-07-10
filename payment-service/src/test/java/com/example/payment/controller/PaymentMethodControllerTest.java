package com.example.payment.controller;
import com.example.payment.dto.PaymentMethodResponseDTO;
import com.example.payment.security.filter.JwtAuthFilter;
import com.example.payment.security.jwt.JwtService;
import com.example.payment.service.PaymentMethodService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PaymentMethodController.class)
class PaymentMethodControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private PaymentMethodService service;

    @Test
    void debeCrearMetodoPago() throws Exception {

        PaymentMethodResponseDTO response =
                new PaymentMethodResponseDTO();

        response.setId(1L);
        response.setClienteId(10L);
        response.setCardHolder("Juan Perez");
        response.setCardNumber("1234567812345678");
        response.setExpirationDate("12/30");
        response.setCreatedAt(LocalDateTime.now());

        when(service.create(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/payment-methods")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                        {
                            "cardHolder":"Juan Perez",
                            "cardNumber":"1234567812345678",
                            "expirationDate":"12/30",
                            "cvv": "123"
                        }
                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clienteId").value(10))
                .andExpect(jsonPath("$.cardHolder").value("Juan Perez"))
                .andExpect(jsonPath("$.cardNumber").value("1234567812345678"))
                .andExpect(jsonPath("$.expirationDate").value("12/30"));

        verify(service).create(any());
    }
}