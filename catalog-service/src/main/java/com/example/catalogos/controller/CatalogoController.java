package com.example.catalogos.controller;

import com.example.catalogos.dto.CategoryRequestDTO;
import com.example.catalogos.dto.CategoryResponseDTO;
import com.example.catalogos.dto.ProductResponseDTO;
import com.example.catalogos.service.CatalogoService;
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
@Tag(name =  "Catalogo",description = "operaciones con categorias")
@RestController
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CatalogoController {

    private final CatalogoService service;

    public CatalogoController(CatalogoService service) {
        this.service = service;
    }

    // CREATE CATEGORY
    @Operation(summary = "Registrar categoria", description = "Registra una categoria de catalogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "categoria registrada", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryRequestDTO.class))),
            @ApiResponse(responseCode = "400",description = "formato invalido")})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO>
    create(@Valid @RequestBody CategoryRequestDTO request) {

        CategoryResponseDTO response = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET CATEGORY BY ID
    @Operation(summary = "Obtener categoria", description = "Obtener cagetoria mediante id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "categoria encontrado"),
            @ApiResponse(responseCode = "404",description = "categoria no encontrada")})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@Parameter(description = "identificador de categoria") @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    // GET ALL CATEGORIES
    @Operation(summary = "Obtener categorias", description = "Obtener todas las categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "categoria encontradas")})
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    // UPDATE CATEGORY
    @Operation(summary = "Actualizar categoria", description = "Actualizar categoria mediante el identificador de categoria")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @Parameter(description = "Identificador de categoria") @PathVariable Long id,
            @Parameter(description = "Datos nuevos de categoria") @Valid @RequestBody CategoryRequestDTO request) {

        return ResponseEntity.ok(service.update(id, request));
    }

    // DELETE CATEGORY
    @Operation(summary = "Eliminar categoria", description = "eliminar categoria por identificador de categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "categoria eliminada"),
            @ApiResponse(responseCode = "404",description = "categoria no encontrada")})
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@Parameter(description = "Identificador de categoria") @PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.ok(
                Map.of("message", "Category deleted successfully"));
    }

    // GET PRODUCTS BY CATEGORY
    @Operation(summary = "Obtener productos", description = "Obtener productos mediante el identificador de categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "productos encontrado")})
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
            @Parameter(description = "Identificador de categoria")  @PathVariable Long id) {

        return ResponseEntity.ok(service.getProductsByCategory(id));
    }
}