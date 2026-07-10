package com.example.ms_users.service;

import com.example.ms_users.client.CustomerClient;
import com.example.ms_users.dto.CustomerRequestDTO;
import com.example.ms_users.dto.LoginRequestDTO;
import com.example.ms_users.dto.LoginResponseDTO;
import com.example.ms_users.dto.RegisterFullRequestDTO;
import com.example.ms_users.exception.exceptions.CustomerCreationException;
import com.example.ms_users.exception.exceptions.UserAlreadyExistsException;
import com.example.ms_users.exception.exceptions.UserNotFoundException;
import com.example.ms_users.model.User;
import com.example.ms_users.repository.UserRepository;
import com.example.ms_users.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
//se hace inyeccion manual ya que ante testing el @autowired no es conveniente por fallos.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CustomerClient customer;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authManager,
            JwtService jwtService,
            CustomerClient customer
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.customer = customer;
    }
    //en caso de fallar hara rockball
    @Transactional
    public User register(RegisterFullRequestDTO request) {

        validateUsername(request.getUsername());

        User user = buildUser(request);

        User savedUser = userRepository.save(user);
        try {
            CustomerRequestDTO customerRequest = buildCustomerRequest(request, savedUser.getId());
            customer.createCustomer(customerRequest, savedUser.getId());

        } catch (Exception e) {
            throw new CustomerCreationException(e.getMessage());
        }
        return savedUser;
    }

    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        String token = jwtService.generateToken(user.getUsername(), user.getRole(), user.getId());

        return new LoginResponseDTO(token, user.getUsername(), user.getRole()
        );
    }

    // METODOS PRIVADOS que validaran, mayor mantenimiento de codigo y limpieza

    private void validateUsername(String username) {

        userRepository.findByUsername(username).ifPresent(user -> {

            throw new UserAlreadyExistsException("Username already exists");
                });
    }

    private User buildUser(RegisterFullRequestDTO request) {

        String role = request.getRole() == null ? "USER" : request.getRole().toUpperCase(); //automaticamente toma rol mayuscula

        return User.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
    }

    private CustomerRequestDTO
    buildCustomerRequest(RegisterFullRequestDTO request, Long userId) {

        CustomerRequestDTO dto = new CustomerRequestDTO();

        dto.setName(request.getName());

        dto.setRut(request.getRut());

        dto.setEmail(request.getEmail());

        dto.setPhone(request.getPhone());

        dto.setAddress(request.getAddress());

        dto.setUserId(userId);

        return dto;
    }
}