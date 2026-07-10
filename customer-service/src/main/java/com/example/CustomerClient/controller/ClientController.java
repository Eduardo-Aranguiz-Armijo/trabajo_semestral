package com.example.CustomerClient.controller;

import com.example.CustomerClient.dto.ClientRequestDTO;
import com.example.CustomerClient.dto.ClientResponseDTO;
import com.example.CustomerClient.model.Client;
import com.example.CustomerClient.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/customer")
@Tag(name =  "registro clientes",description = "Registro de clientes desde el formulario autenticacion")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }
    @Operation(summary = "Registrar Cliente", description = "Registra el cliente con los datos solicitantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "registro exitoso", content =  @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClientRequestDTO.class))),
            @ApiResponse(responseCode = "400",description = "datos invalidos")})

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody ClientRequestDTO request) {
        Client client = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "obtener clientes", description = "retorna todos los clientes con el id de usuario")
    @ApiResponse(responseCode = "200",description = "clientes retornados")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "buscar cliente", description = "busca cliente con el id de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "cliente encontrado"),
            @ApiResponse(responseCode = "404",description = "cliente no encontrado")})
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> findById(@Parameter(description = "codigo del cliente", required = true) @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "buscar usuario", description = "busca usuario con el id de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "usuario encontrado"),
            @ApiResponse(responseCode = "404",description = "usuario no encontrado")})
    @GetMapping("/user/{userId}")
    public ResponseEntity<ClientResponseDTO> getByUserId(@Parameter(description = "codigo del usuario", required = true) @PathVariable Long userId) {

        return ResponseEntity.ok(service.getByUserId(userId));
    }
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "actualizar cliente", description = "actualiza el cliente con  los datos nuevos y el id de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "cliente actualizado",content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientRequestDTO.class))),
            @ApiResponse(responseCode = "404",description = "cliente no encontrado")})

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Client> updateClient(@Valid @RequestBody ClientRequestDTO request,@Parameter(description = "codigo del cliente", required = true) @PathVariable Long id) {

        return ResponseEntity.ok(service.update(request, id));
    }
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "borrar cliente", description = "borra el cliente mediante el id del cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "cliente encontrado"),
            @ApiResponse(responseCode = "404",description = "cliente no encontrado")})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteById(@Parameter(description = "codigo del cliente", required = true) @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Client deleted successfully"));
    }
}