package com.example.productos.controller;

import com.example.productos.dto.ProductFullRequestDTO;
import com.example.productos.dto.ProductResponseDTO;
import com.example.productos.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.productos.security.jwt.JwtService;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypass security filters for unit testing controller logic
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductResponseDTO responseDTO;
    private ProductFullRequestDTO fullRequestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = new ProductResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test Product");
        responseDTO.setPrice(100.0);
        responseDTO.setCategoryId(2L);

        fullRequestDTO = new ProductFullRequestDTO();
        fullRequestDTO.setName("Test Product");
        fullRequestDTO.setDescription("Desc");
        fullRequestDTO.setPrice(100.0);
        fullRequestDTO.setCategoryId(2L);
        fullRequestDTO.setStock(10);
    }

    @Test
    void testGetAll() throws Exception {
        when(productService.getAll()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetById() throws Exception {
        when(productService.getById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testCreate() throws Exception {
        when(productService.createFullProduct(any(ProductFullRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fullRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }
}
