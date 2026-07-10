package com.example.productos.controller;

import com.example.productos.dto.ProductFullRequestDTO;
import com.example.productos.dto.ProductResponseDTO;
import com.example.productos.security.jwt.JwtService;
import com.example.productos.service.ProductService;
import org.springframework.hateoas.MediaTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private ProductService service;

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaCrearProducto() throws Exception {

        ProductFullRequestDTO request = new ProductFullRequestDTO();
        request.setName("Laptop");
        request.setDescription("HP");
        request.setPrice(2500.0);
        request.setCategoryId(1L);
        request.setStock(10);

        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(1L);
        response.setName("Laptop");

        when(service.createFullProduct(any(ProductFullRequestDTO.class)))
                .thenReturn(response);

        String json = """
                        {
                          "name": "Laptop",
                          "description": "HP",
                          "price": 2500.0,
                          "categoryId": 1,
                          "stock": 10
                        }
                        """;

        mockMvc.perform(post("/api/v1/products")
                        .contentType("application/json")
                        .accept(MediaTypes.HAL_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"));

        verify(service).createFullProduct(any(ProductFullRequestDTO.class));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaObtenerProductoPorId() throws Exception {

        // Arrange (preparar)
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(1L);
        response.setName("Laptop");

        when(service.getById(1L))
                .thenReturn(response);

        // Act + Assert
        mockMvc.perform(get("/api/v1/products/{id}", 1L)
                        .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"));

        // Verificar que el controlador llamó al servicio
        verify(service).getById(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaRetornarTodosLosProductos() throws Exception {
        ProductResponseDTO response1 = new ProductResponseDTO();
        response1.setId(1L);
        response1.setName("Laptop");
        ProductResponseDTO response2 = new ProductResponseDTO();
        response2.setId(2L);
        response2.setName("Teclado");
        ProductResponseDTO response3 = new ProductResponseDTO();
        response3.setId(3L);
        response3.setName("Mouse");

        when(service.getAll()).thenReturn(List.of(response1,response2,response3));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].name")
                        .value(containsInAnyOrder("Laptop", "Teclado", "Mouse")));

        verify(service).getAll();


    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaObtenerProductoPorIdCategory() throws Exception {

        // Arrange (preparar)
        ProductResponseDTO response1 = new ProductResponseDTO();
        response1.setCategoryId(2L);
        response1.setName("Laptop");
        ProductResponseDTO response2 = new ProductResponseDTO();
        response2.setCategoryId(2L);
        response2.setName("Teclado");
        ProductResponseDTO response3 = new ProductResponseDTO();
        response3.setCategoryId(2L);
        response3.setName("Mouse");

        when(service.getByCategory(1L))
                .thenReturn(List.of(response1,response2,response3));

        // Act + Assert
        mockMvc.perform(get("/api/v1/products/category/{categoryId}", 1L))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].name").value(containsInAnyOrder("Laptop", "Teclado", "Mouse")));

        // Verificar que el controlador llamó al servicio
        verify(service).getByCategory(1L);
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void deberiaEliminarProductoSiExiste() throws Exception {

        // Act + Assert
        mockMvc.perform(delete("/api/v1/products/{id}", 5L))
                .andExpect(status().isNoContent());

        // Verificar que el controlador llamó al servicio
        verify(service).delete(5L);
    }

}