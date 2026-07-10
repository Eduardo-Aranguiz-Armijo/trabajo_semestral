package com.example.carrito.controller;

import com.example.carrito.dto.CartResponseDTO;
import com.example.carrito.service.CartService;
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

import com.example.carrito.security.jwt.JwtService;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypass security filters for unit testing controller logic
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;
    
    @MockBean
    private JwtService jwtService;

    private CartResponseDTO dummyCart;

    @BeforeEach
    void setUp() {
        dummyCart = new CartResponseDTO();
        dummyCart.setId(1L);
        dummyCart.setClientId(10L);
        dummyCart.setStatus("ACTIVE");
    }

    @Test
    void testCreateCart() throws Exception {
        when(cartService.createCart()).thenReturn(dummyCart);

        mockMvc.perform(post("/api/v1/cart"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void testGetMyCart() throws Exception {
        when(cartService.getMyCart()).thenReturn(dummyCart);

        mockMvc.perform(get("/api/v1/cart/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetMyTotal() throws Exception {
        when(cartService.getMyTotal()).thenReturn(150.0);

        mockMvc.perform(get("/api/v1/cart/total-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(150.0));
    }

    @Test
    void testGetAllCarts() throws Exception {
        when(cartService.getAllCarts()).thenReturn(List.of(dummyCart));

        mockMvc.perform(get("/api/v1/cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
