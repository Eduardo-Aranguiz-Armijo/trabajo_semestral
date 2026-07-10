package com.example.carrito.controller;

import com.example.carrito.dto.CartItemRequestDTO;
import com.example.carrito.dto.CartItemResponseDTO;
import com.example.carrito.service.CartItemService;
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
@Tag(name = "item-carrito", description = "Gestión de items del carrito")
@RestController
@RequestMapping("/api/v1/cart/items")
public class CartItemController {

    private final CartItemService service;

    public CartItemController(CartItemService service) {
        this.service = service;
    }

    // CREATE ITEM

    @Operation(summary = "Agregar item al carrito", description = "Agrega un producto al carrito del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item agregado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Formato de item inválido")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CartItemResponseDTO> createItem(@Valid @RequestBody CartItemRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.createItem(request));
    }

    // GET MY ITEMS

    @Operation(summary = "Obtener mis items", description = "Obtiene todos los items del carrito del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemResponseDTO.class)))
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<List<CartItemResponseDTO>> getMyItems() {

        return ResponseEntity.ok(service.getMyItems());
    }

    // UPDATE ITEM

    @Operation(summary = "Actualizar item", description = "Actualiza un item del carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item actualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Formato de item inválido"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> updateItem(@Parameter(description = "ID del item") @PathVariable Long id,
                                                          @Valid @RequestBody CartItemRequestDTO request) {
        return ResponseEntity.ok(service.updateItem(id, request));
    }

    // DELETE ITEM

    @Operation(summary = "Eliminar item", description = "Elimina un item del carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item eliminado"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @Parameter(description = "ID del item") @PathVariable Long id) {
        service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    // ADMIN - ITEMS BY CART

    @Operation(summary = "Obtener items por carrito", description = "Obtiene todos los items de un carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItemResponseDTO>> getItemsByCart(
            @Parameter(description = "ID del carrito") @PathVariable Long cartId) {

        return ResponseEntity.ok(service.getItemsByCart(cartId));
    }
}