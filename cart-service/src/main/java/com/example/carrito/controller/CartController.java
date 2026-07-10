package com.example.carrito.controller;

import com.example.carrito.dto.CartRequestDTO;
import com.example.carrito.dto.CartResponseDTO;
import com.example.carrito.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "carrito", description = "Gestión de carritos")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    // =========================================
    // CREATE CART
    // =========================================

    @Operation(summary = "Crear carrito", description = "Crea un nuevo carrito para el usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")})
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart() {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCart());
    }

    // =========================================
    // GET MY CART
    // =========================================

    @Operation(summary = "Obtener mi carrito", description = "Obtiene el carrito del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CartResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "No fue posible obtener el carrito")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<CartResponseDTO> getMyCart() {

        return ResponseEntity.ok(service.getMyCart());
    }

    // =========================================
    // CANCEL MY CART
    // =========================================

    @Operation(summary = "Cancelar mi carrito", description = "Cancela el carrito del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito cancelado correctamente"),
            @ApiResponse(responseCode = "400", description = "No fue posible cancelar el carrito")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/me")
    public ResponseEntity<Void> cancelMyCart() {

        service.cancelMyCart();
        return ResponseEntity.noContent().build();
    }

    // =========================================
    // GET MY TOTAL
    // =========================================

    @Operation(summary = "Obtener total del carrito", description = "Obtiene el precio total del carrito del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "No fue posible obtener el total")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/total-price")
    public ResponseEntity<Double> getMyTotal() {

        return ResponseEntity.ok(service.getMyTotal());
    }

    // =========================================
    // ADMIN METHODS
    // =========================================

    @Operation(summary = "Listar carritos", description = "Obtiene todos los carritos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carritos obtenida",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAllCarts() {
        return ResponseEntity.ok(service.getAllCarts());
    }

    @Operation(summary = "Obtener carrito por ID", description = "Obtiene un carrito mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCartById(@Parameter(description = "ID del carrito") @PathVariable Long id) {

        return ResponseEntity.ok(service.getCartById(id));
    }

    @Operation(summary = "Actualizar carrito", description = "Actualiza la información de un carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del carrito inválidos"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> updateCart(@Parameter(description = "ID del carrito") @PathVariable Long id, @Valid @RequestBody CartRequestDTO request) {
        return ResponseEntity.ok(service.updateCart(id, request));
    }

    @Operation(summary = "Eliminar carrito", description = "Elimina un carrito mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Identificador de carrito inválido")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@Parameter(description = "ID del carrito") @PathVariable Long id) {

        service.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}