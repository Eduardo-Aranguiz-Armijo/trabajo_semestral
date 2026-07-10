package com.example.ms_users.controller;

import com.example.ms_users.dto.LoginRequestDTO;
import com.example.ms_users.dto.LoginResponseDTO;
import com.example.ms_users.model.User;
import com.example.ms_users.security.jwt.JwtService;
import com.example.ms_users.service.CustomUserDetailsService;
import com.example.ms_users.service.UserService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private  AuthenticationManager authManager;
    @MockitoBean
    private  JwtService jwtService;
    @MockitoBean
    private  UserService userService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;
    @Test
    @WithMockUser(roles = "ADMIN")
    void retornarUsuarioPorId() throws Exception {
        User user = new User();
        user.setUsername("suarez123");
        user.setId(1L);

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/auth/id/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("suarez123"));

        verify(userService).findById(1L);

    }
    @Test
    void debeHacerLoginCorrectamente() throws Exception {

        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("juan");
        request.setPassword("1234");

        LoginResponseDTO responseDTO = new LoginResponseDTO();
        responseDTO.setToken("fake-jwt");

        when(userService.login(any(LoginRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "username": "juan",
                        "password": "1234"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt"));

        verify(userService).login(any(LoginRequestDTO.class));
    }

    @Test
    void debeBuscarUsuarioPorUsername() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setUsername("juan");

        when(userService.findByUsername("juan"))
                .thenReturn(user);

        mockMvc.perform(get("/api/v1/auth/juan")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("juan"));

        verify(userService).findByUsername("juan");
    }

}
