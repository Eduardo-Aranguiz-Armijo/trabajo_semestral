package com.example.inventory.controller;

import com.example.inventory.dto.InventoryRequestDTO;
import com.example.inventory.dto.InventoryResponseDTO;
import com.example.inventory.service.InventoryService;
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
@RestController
@RequestMapping("/api/v1/inventory")
@SecurityRequirement(name = "bearerAuth")
@Tag(name =  "inventario",description = "Registro de productos en inventario")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // CREATE
    @Operation(summary = "Registrar Producto", description = "Registra producto automaticamente desde el servicio productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "producto registrado", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InventoryRequestDTO.class))),
            @ApiResponse(responseCode = "400",description = "formato de producto invalido")})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> create(@Valid @RequestBody InventoryRequestDTO request) {

        InventoryResponseDTO response = service.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET BY PRODUCT
    @Operation(summary = "Obtener producto", description = "Obtener producto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "producto encontrado"),
            @ApiResponse(responseCode = "404",description = "producto no encontrado")})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryResponseDTO>
    getByProduct(@Parameter(description = "codigo del producto") @PathVariable Long productId) {

        return ResponseEntity.ok(service.getByProduct(productId));
    }

    // UPDATE STOCK
    @Operation(summary = "actualizar stock", description = "actualizar cantidad de un producto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "cantidad actualizada"),
            @ApiResponse(responseCode = "404",description = "producto no encontrado")})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{productId}")
    public ResponseEntity<InventoryResponseDTO>
    updateStock(@Parameter(description = "codigo del producto") @PathVariable Long productId,@Parameter(description = "cantidad a actualizar") @RequestParam Integer stock) {

        return ResponseEntity.ok(service.updateStock(productId, stock));
    }

    // DECREASE STOCK
    @Operation(summary = "reducir stock", description = "reduce stock automaticamente al realizar una orden en servicio orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "cantidad reducida"),
            @ApiResponse(responseCode = "404",description = "producto no encontrado")})
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/product/{productId}/decrease")
    public ResponseEntity<InventoryResponseDTO> decreaseStock(@Parameter(description = "codigo del producto") @PathVariable Long productId,@Parameter(description = "cantidad stock a reducir") @RequestParam Integer quantity) {

        return ResponseEntity.ok(service.decreaseStock(productId, quantity));
    }
    //INCREASE STOCK
    @Operation(summary = "recuperar stock", description = "recupera el stock tras cancelar orden o eliminacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "cantidad recuperada"),
            @ApiResponse(responseCode = "404",description = "producto no encontrado")})
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/product/{productId}/increase")
    public ResponseEntity<InventoryResponseDTO> increaseStock(@Parameter(description = "codigo del producto") @PathVariable Long productId,@Parameter(description = "cantidad de stock a recuperar") @RequestParam Integer quantity) {

        return ResponseEntity.ok(service.increaseStock(productId, quantity));
    }
}