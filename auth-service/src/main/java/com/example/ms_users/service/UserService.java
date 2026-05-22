package com.example.ms_users.service;

import com.example.ms_users.client.CustomerClient;
import com.example.ms_users.dto.CustomerRequestDTO;
import com.example.ms_users.dto.LoginRequestDTO;
import com.example.ms_users.dto.LoginResponseDTO;
import com.example.ms_users.dto.RegisterFullRequestDTO;
import com.example.ms_users.model.User;
import com.example.ms_users.repository.UserRepository;
import com.example.ms_users.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    //NOTA: al script le falta mucho, esto solo es una version ejecutable de registro entre 2 microservicios
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerClient customer;

    // Registro con rol especifico
    public User register(RegisterFullRequestDTO request) {
        //crea al usuario con su username, password y rol
        User user = User.builder()
                //extrae el DTO completo que enviamos, (extrae para guardar solo usuario)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "USER") // Por defecto USER
                .build();
        User saveUser = userRepository.save(user);
        //extrae el DTO completo que enviamos, solo extrae la informacion que sera guardada en cliente
        CustomerRequestDTO customerRequest = new CustomerRequestDTO();
        customerRequest.setNombre(request.getNombre());
        customerRequest.setRut(request.getRut());
        customerRequest.setCorreo(request.getCorreo());
        customerRequest.setTelefono(request.getTelefono());
        customerRequest.setDireccion(request.getDireccion());
        customerRequest.setUserId(saveUser.getId());
        //se crea un cliente automaticamente, conectado al user id de usuario
        customer.createCustomer(customerRequest,customerRequest.getUserId());
        return saveUser;
    }

    // no teste esto
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    //funciona bien
    public LoginResponseDTO login(LoginRequestDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();
        // Spring Security valida credenciales
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Buscar usuario para obtener rol
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado")
                );

        // Generar JWT
        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole(),
                user.getId()
        );

        // Respuesta
        return new LoginResponseDTO(token, user.getUsername(), user.getRole());
    }
}

