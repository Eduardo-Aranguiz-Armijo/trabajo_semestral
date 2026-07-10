package com.example.payment.controller;

import com.example.payment.dto.PaymentResponseDTO;
import com.example.payment.security.filter.JwtAuthFilter;
import com.example.payment.security.jwt.JwtService;
import com.example.payment.service.PaymentService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService service;
    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;
    @MockitoBean
    private JwtService jwtService;

    @Test
    void debeCrearPago() throws Exception {

        PaymentResponseDTO response =
                new PaymentResponseDTO();

        response.setId(1L);
        response.setOrderId(10L);
        response.setClienteId(20L);
        response.setPaymentMethodId(30L);
        response.setAmount(15000.0);
        response.setStatus("PAID");
        response.setPaidAt(LocalDateTime.now());

        when(service.create(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/payments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                  "orderId": 10,
                                  "paymentMethodId": 30
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderId").value(10))
                .andExpect(jsonPath("$.clienteId").value(20))
                .andExpect(jsonPath("$.paymentMethodId").value(30))
                .andExpect(jsonPath("$.amount").value(15000.0))
                .andExpect(jsonPath("$.status").value("PAID"));

        verify(service).create(any());
    }

    @Test
    void debeRetornarPagoPorId() throws Exception {

        PaymentResponseDTO response =
                new PaymentResponseDTO();

        response.setId(1L);
        response.setOrderId(10L);
        response.setClienteId(20L);
        response.setPaymentMethodId(30L);
        response.setAmount(15000.0);
        response.setStatus("PAID");
        response.setPaidAt(LocalDateTime.now());

        when(service.getById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/payments/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderId").value(10))
                .andExpect(jsonPath("$.clienteId").value(20))
                .andExpect(jsonPath("$.paymentMethodId").value(30))
                .andExpect(jsonPath("$.amount").value(15000.0))
                .andExpect(jsonPath("$.status").value("PAID"));

        verify(service).getById(1L);
    }
}