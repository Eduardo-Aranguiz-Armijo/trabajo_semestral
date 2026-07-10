package com.example.inventory.controller;

import com.example.inventory.dto.InventoryRequestDTO;
import com.example.inventory.dto.InventoryResponseDTO;
import com.example.inventory.security.jwt.JwtService;
import com.example.inventory.service.InventoryService;
import org.springframework.hateoas.MediaTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PutMapping;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(InventoryController.class)

public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private InventoryService service;
    @MockitoBean
    private JwtService jwtService;
    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearInventario() throws Exception {

        // Arrange
        InventoryResponseDTO response = new InventoryResponseDTO();
        response.setId(1L);
        response.setProductId(5L);
        response.setStock(20);

        when(service.create(any(InventoryRequestDTO.class)))
                .thenReturn(response);

        String json = """
            {
              "productId": 5,
              "stock": 20
            }
            """;

        // Act + Assert
        mockMvc.perform(post("/api/v1/inventory")
                        .contentType(MediaTypes.HAL_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productId").value(5))
                .andExpect(jsonPath("$.stock").value(20));

        // Verify
        verify(service).create(any(InventoryRequestDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaActualizarStock() throws Exception {

        InventoryResponseDTO response =
                new InventoryResponseDTO();

        response.setId(1L);
        response.setProductId(5L);
        response.setStock(50);

        when(service.updateStock(5L, 50))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/inventory/product/{productId}", 5L)
                        .param("stock", "50")
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productId").value(5))
                .andExpect(jsonPath("$.stock").value(50));

        verify(service).updateStock(5L, 50);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaDisminuirStock() throws Exception {

        InventoryResponseDTO response =
                new InventoryResponseDTO();

        response.setId(1L);
        response.setProductId(5L);
        response.setStock(15);

        when(service.decreaseStock(5L, 5))
                .thenReturn(response);

        mockMvc.perform(
                        put("/api/v1/inventory/product/{productId}/decrease", 5L)
                                .param("quantity", "5")
                                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productId").value(5))
                .andExpect(jsonPath("$.stock").value(15));

        verify(service).decreaseStock(5L, 5);
    }
}