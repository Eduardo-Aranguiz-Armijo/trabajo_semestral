package com.example.catalogos.controller;

import com.example.catalogos.dto.CategoryRequestDTO;
import com.example.catalogos.dto.CategoryResponseDTO;
import com.example.catalogos.security.filter.JwtAuthFilter;
import com.example.catalogos.security.jwt.JwtService;
import com.example.catalogos.service.CatalogoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogoController.class)
@AutoConfigureMockMvc(addFilters = false)
class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogoService service;
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;
    @Test
    void debeCrearCategoria() throws Exception {

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setId(1L);
        response.setName("Electrónica");

        when(service.create(any(CategoryRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Electrónica",
                        "description": "Dispositivos"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Electrónica"));

        verify(service).create(any(CategoryRequestDTO.class));
    }
    @Test
    void debeObtenerCategoriaPorId() throws Exception {

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setId(1L);
        response.setName("Electrónica");

        when(service.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Electrónica"));

        verify(service).getById(1L);
    }
    @Test
    void debeListarCategorias() throws Exception {

        CategoryResponseDTO c1 = new CategoryResponseDTO();
        c1.setId(1L);
        c1.setName("A");

        CategoryResponseDTO c2 = new CategoryResponseDTO();
        c2.setId(2L);
        c2.setName("B");

        when(service.getAll()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].name").value("B"));

        verify(service).getAll();
    }
    @Test
    void debeActualizarCategoria() throws Exception {

        CategoryResponseDTO response = new CategoryResponseDTO();
        response.setId(1L);
        response.setName("Nuevo");

        when(service.update(eq(1L), any(CategoryRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Nuevo",
                        "description": "Actualizado"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nuevo"));

        verify(service).update(eq(1L), any(CategoryRequestDTO.class));
    }
    @Test

    void debeEliminarCategoria() throws Exception {

        mockMvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
}