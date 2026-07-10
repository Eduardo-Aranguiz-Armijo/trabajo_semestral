package com.example.orden.controller;

import com.example.orden.dto.OrderResponseDTO;
import com.example.orden.service.OrdenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "orden", description = "Gestión de órdenes")
@RestController
@RequestMapping("/api/v1/orders")
public class OrdenController {

    private final OrdenService service;

    public OrdenController(OrdenService service) {
        this.service = service;
    }

    // =========================================
    // CREATE ORDER
    // =========================================

    @Operation(summary = "Crear orden", description = "Genera una orden a partir del carrito del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "No fue posible crear la orden")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder() {

        OrderResponseDTO response = service.createOrder();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =========================================
    // GET MY ORDERS
    // =========================================

    @Operation(summary = "Obtener mis órdenes", description = "Obtiene todas las órdenes del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)))
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders() {

        return ResponseEntity.ok(service.getMyOrders());
    }

    // =========================================
    // GET ORDER BY ID
    // =========================================

    @Operation(summary = "Obtener orden por ID", description = "Obtiene una orden mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@Parameter(description = "ID de la orden") @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    // =========================================
    // GET ALL ORDERS
    // =========================================

    @Operation(summary = "Listar órdenes", description = "Obtiene todas las órdenes registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes encontradas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // =========================================
    // UPDATE STATUS
    // =========================================

    @Operation(summary = "Actualizar estado", description = "Actualiza el estado de una orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@Parameter(description = "ID de la orden") @PathVariable Long id,
                                                         @Parameter(description = "Nuevo estado de la orden") @RequestParam String status) {

        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    // =========================================
    // CANCEL MY ORDER
    // =========================================

    @Operation(summary = "Cancelar orden", description = "Cancela una orden del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden cancelada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelMyOrder(
            @Parameter(description = "ID de la orden")
            @PathVariable Long id) {

        return ResponseEntity.ok(service.cancelMyOrder(id));
    }

    // =========================================
    // DELETE ORDER
    // =========================================

    @Operation(summary = "Eliminar orden", description = "Elimina una orden mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Orden eliminada"),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "ID de la orden")
            @PathVariable Long id) {

        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}