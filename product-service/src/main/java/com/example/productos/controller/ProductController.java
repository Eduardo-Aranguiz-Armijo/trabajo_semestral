package com.example.productos.controller;

import com.example.productos.dto.ProductFullRequestDTO;
import com.example.productos.dto.ProductRequestDTO;
import com.example.productos.dto.ProductResponseDTO;
import com.example.productos.service.ProductService;
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
import java.util.Map;
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "producto", description = "Registro de productos")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // CREATE

    @Operation(summary = "Registrar Producto", description = "Registra un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto registrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Formato de producto inválido")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @Valid @RequestBody ProductFullRequestDTO request) {
        ProductResponseDTO response = service.createFullProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET BY ID

    @Operation(summary = "Obtener Producto por ID", description = "Obtiene la información de un producto mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(
            @Parameter(description = "ID del producto")    @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    // GET ALL
    @Operation(summary = "Listar Productos", description = "Obtiene el listado de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class)))
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    // UPDATE

    @Operation(summary = "Actualizar Producto", description = "Actualiza la información de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos del producto inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @Parameter(description = "ID del producto")  @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request) {

        return ResponseEntity.ok(service.update(id, request));
    }

    // DELETE

    @Operation(summary = "Eliminar Producto", description = "Elimina un producto mediante su identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "producto no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @Parameter(description = "ID del producto")   @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
    }

    // GET BY CATEGORY

    @Operation(summary = "Listar Productos por Categoría", description = "Obtiene todos los productos de una categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "categoria no encontrada")
    })
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(
            @Parameter(description = "ID de la categoría")   @PathVariable Long categoryId) {

        return ResponseEntity.ok(service.getByCategory(categoryId));
    }
}