package com.example.ms_users.controller;

import com.example.ms_users.dto.LoginRequestDTO;
import com.example.ms_users.dto.LoginResponseDTO;
import com.example.ms_users.dto.RegisterFullRequestDTO;
import com.example.ms_users.model.User;
import com.example.ms_users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "bearerAuth")
@Tag(name =  "autenticacion",description = "Registro y autenticacion de usuarios")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @Operation(summary = "registra usuario", description = "permite iniciar sesion a los usuarios con los datos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "registro exitoso"),
            @ApiResponse(responseCode = "400",description = "datos erroneos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterFullRequestDTO.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterFullRequestDTO request) {
        userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User registered successfully"));
    }

    @Operation(summary = "Logea usuario", description = "permite iniciar sesion a los usuarios con los datos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "inicio de sesion exitoso"),
            @ApiResponse(responseCode = "400",description = "usuario o clave incorrectas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginRequestDTO.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Buscar usuario", description = "buscar el usuario con el nombre de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "usuario encontrado"),
            @ApiResponse(responseCode = "404",description = "usuario no encontrado")})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{username}")
    public ResponseEntity<User>
    findByUsername(@Parameter(description = "codigo del usuario", required = true)  @PathVariable String username) {

        User user = userService.findByUsername(username);

        return ResponseEntity.ok(user);
    }
}