package com.example.CustomerClient.controller;

import com.example.CustomerClient.dto.ClientRequestDTO;
import com.example.CustomerClient.dto.ClienteResponseDTO;
import com.example.CustomerClient.model.Client;
import com.example.CustomerClient.security.filter.JwtAuthFilter;
import com.example.CustomerClient.security.jwt.JwtService;
import com.example.CustomerClient.service.ClientService;
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

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService service;
    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void debeCrearCliente() throws Exception {

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "nombre": "Juan",
                        "rut": "123",
                        "correo": "test@mail.com",
                        "direccion": "Santiago",
                        "telefono": "999",
                        "userId": 1
                    }
                    """))
                .andExpect(status().isOk());

        verify(service).create(any(ClientRequestDTO.class));
    }
    @Test
    void debeListarClientes() throws Exception {

        Client c1 = new Client();
        c1.setIdCustomer(1L);
        c1.setName("Juan");

        Client c2 = new Client();
        c2.setIdCustomer(2L);
        c2.setName("Pedro");

        when(service.findAll()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/v1/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Juan"))
                .andExpect(jsonPath("$[1].name").value("Pedro"));

        verify(service).findAll();
    }
    @Test

    void debeBuscarPorId() throws Exception {

        Client client = new Client();
        client.setIdCustomer(1L);
        client.setName("Juan");

        when(service.findById(1L)).thenReturn(client);

        mockMvc.perform(get("/api/v1/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCustomer").value(1))
                .andExpect(jsonPath("$.name").value("Juan"));

        verify(service).findById(1L);
    }
    @Test
    void debeObtenerPorUserId() throws Exception {

        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(1L);
        dto.setUserId(10L);
        dto.setNombre("Juan");

        when(service.getByUserId(10L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/customer/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(10))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.id").value(1));

        verify(service).getByUserId(10L);
    }
    @Test

    void debeActualizarCliente() throws Exception {

        Client updated = new Client();
        updated.setIdCustomer(1L);
        updated.setName("Nuevo");

        when(service.update(any(ClientRequestDTO.class), eq(1L)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/v1/customer/update/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "nombre": "Nuevo",
                        "rut": "999",
                        "correo": "x@mail.com",
                        "direccion": "Valpo",
                        "telefono": "111",
                        "userId": 2
                    }
                    """))
                .andExpect(status().isOk());

        verify(service).update(any(ClientRequestDTO.class), eq(1L));
    }
}