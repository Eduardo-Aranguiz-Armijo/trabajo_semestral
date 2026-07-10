package com.example.ms_users.service;

import com.example.ms_users.client.CustomerClient;
import com.example.ms_users.dto.CustomerRequestDTO;
import com.example.ms_users.dto.LoginRequestDTO;
import com.example.ms_users.dto.LoginResponseDTO;
import com.example.ms_users.dto.RegisterFullRequestDTO;
import com.example.ms_users.model.User;
import com.example.ms_users.repository.UserRepository;
import com.example.ms_users.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {



    @Mock
    private AuthenticationManager authManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CustomerClient customer;
    @InjectMocks
    private UserService service;

    @Test
    void debeDevolverUsuarioPorId(){
        User user = new User();
        user.setId(2L);
        user.setUsername("suarez27");

        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
       User resultado = userRepository.findById(2L).orElseThrow();

        assertEquals(2, resultado.getId());

        assertEquals("suarez27",resultado.getUsername());
        verify(userRepository).findById(2L);

    }

    @Test
    void debeDevolverUsuarioPorUsername(){
        User user = new User();
        user.setId(2L);
        user.setUsername("suarez27gamer");

        when(userRepository.findByUsername("suarez27gamer"))
                .thenReturn(Optional.of(user));


        User resultado = userRepository.findByUsername("suarez27gamer").orElseThrow();

        assertEquals(2, resultado.getId());
        assertEquals("suarez27gamer",resultado.getUsername());
        verify(userRepository).findByUsername("suarez27gamer");
    }

    @Test
    void debeRegistrarUsuariosCorrectamente(){
        RegisterFullRequestDTO request = new RegisterFullRequestDTO();
        request.setUsername("juan");
        request.setPassword("1234");
        request.setRole("ADMIN");

        User userGuardado = User.builder()
                .id(1L)
                .username("juan")
                .password("encoded-pass")
                .role("ADMIN")
                .build();
        when(passwordEncoder.encode("1234")).thenReturn("encode-pass");
        when(userRepository.save(any(User.class))).thenReturn(userGuardado);

        User resultado = service.register(request);
        assertNotNull(resultado);
        assertEquals(1,resultado.getId());
        assertEquals("encoded-pass",resultado.getPassword());
        assertEquals("ADMIN",resultado.getRole());
        verify(passwordEncoder).encode("1234");
        verify(userRepository).save(any(User.class));
        verify(customer).createCustomer(any(CustomerRequestDTO.class), any());
    }
    @Test
    void debeLogearUsuarioCuandoConCredenciales(){
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("juan");
        request.setPassword("USER");

        User user = new User();
        user.setId(1L);
        user.setUsername("juan");
        user.setRole("USER");

        when(userRepository.findByUsername("juan")).thenReturn(Optional.of(user));

        when(jwtService.generateToken("juan", "USER", 1L))
                .thenReturn("fake-jwt-token");

        LoginResponseDTO response = service.login(request);
        assertNotNull(response);

        assertEquals("fake-jwt-token", response.getToken());
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername("juan");
        verify(jwtService).generateToken("juan", "USER", 1L);
    }
}