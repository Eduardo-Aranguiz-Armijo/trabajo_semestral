package com.example.orden.controller;

import com.example.orden.dto.OrderResponseDTO;
import com.example.orden.service.OrdenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.orden.security.jwt.JwtService;

@WebMvcTest(OrdenController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypass security filters for unit testing controller logic
class OrdenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdenService ordenService;
    
    @MockBean
    private JwtService jwtService;

    private OrderResponseDTO dummyOrderResponse;

    @BeforeEach
    void setUp() {
        dummyOrderResponse = new OrderResponseDTO();
        dummyOrderResponse.setId(1L);
        dummyOrderResponse.setClientId(2L);
        dummyOrderResponse.setCartId(3L);
        dummyOrderResponse.setStatus("PENDING_PAYMENT");
        dummyOrderResponse.setTotal(150.0);
    }

    @Test
    void testCreateOrder() throws Exception {
        when(ordenService.createOrder()).thenReturn(dummyOrderResponse);

        mockMvc.perform(post("/api/v1/orders"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.total").value(150.0));
    }

    @Test
    void testGetMyOrders() throws Exception {
        when(ordenService.getMyOrders()).thenReturn(List.of(dummyOrderResponse));

        mockMvc.perform(get("/api/v1/orders/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetById() throws Exception {
        when(ordenService.getById(1L)).thenReturn(dummyOrderResponse);

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("PENDING_PAYMENT"));
    }

    @Test
    void testGetAll() throws Exception {
        when(ordenService.getAll()).thenReturn(List.of(dummyOrderResponse));

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
