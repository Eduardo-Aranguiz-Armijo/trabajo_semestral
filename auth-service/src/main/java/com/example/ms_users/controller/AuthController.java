package com.example.ms_users.controller;

import com.example.ms_users.dto.LoginRequestDTO;
import com.example.ms_users.dto.LoginResponseDTO;
import com.example.ms_users.dto.RegisterFullRequestDTO;
import com.example.ms_users.model.User;
import com.example.ms_users.security.jwt.JwtService;
import com.example.ms_users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    //es solo una parte ejecutable, aun queda poner coherencia en las respuestas http como registrar = 201
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserService userService;
    //inyeccion manual sin autowired
    public AuthController(AuthenticationManager authManager,
                          JwtService jwtService,
                          UserService userService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterFullRequestDTO request) {
        userService.register(request);
        //retorna mensaje positivo al json
        return ResponseEntity.ok(Map.of(
                "message", "Usuario registrado correctamente"
        ));
    }
    //aqui hacia abajo no teste
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = userService.login(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }
}