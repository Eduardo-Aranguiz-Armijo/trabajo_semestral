package com.example.productos.controller;

import com.example.productos.dto.ProductFullRequestDTO;
import com.example.productos.dto.ProductRequestDTO;
import com.example.productos.dto.ProductResponseDTO;
import com.example.productos.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Endpoints para la gestión de productos y categorías")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // 🔥 CREATE PRODUCT
    @Operation(summary = "Crear producto completo", description = "Crea un producto y su inventario inicial (Solo Administradores)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado"),
            @ApiResponse(responseCode = "403", description = "Sin permisos de administrador")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @RequestBody ProductFullRequestDTO request) {

        ProductResponseDTO response = service.createFullProduct(request);
        response.add(linkTo(methodOn(ProductController.class).getById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(ProductController.class).getAll()).withRel("todos"));
        response.add(linkTo(methodOn(ProductController.class).update(response.getId(), null)).withRel("update"));
        response.add(linkTo(methodOn(ProductController.class).delete(response.getId())).withRel("delete"));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    // 🔥 GET PRODUCT BY ID
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto y devuelve su información detallada")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(
            @PathVariable Long id) {

        ProductResponseDTO response = service.getById(id);
        response.add(linkTo(methodOn(ProductController.class).getById(id)).withSelfRel());
        response.add(linkTo(methodOn(ProductController.class).getAll()).withRel("todos"));
        response.add(linkTo(methodOn(ProductController.class).update(id, null)).withRel("update"));
        response.add(linkTo(methodOn(ProductController.class).delete(id)).withRel("delete"));

        return ResponseEntity.ok(response);
    }

    // 🔥 GET ALL PRODUCTS
    @Operation(summary = "Obtener todos los productos", description = "Retorna la lista completa de productos")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    // 🔥 UPDATE PRODUCT
    @Operation(summary = "Actualizar producto", description = "Modifica los datos básicos de un producto (Solo Administradores)")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO request) {

        ProductResponseDTO response = service.update(id, request);
        response.add(linkTo(methodOn(ProductController.class).getById(id)).withSelfRel());
        response.add(linkTo(methodOn(ProductController.class).getAll()).withRel("todos"));
        response.add(linkTo(methodOn(ProductController.class).delete(id)).withRel("delete"));

        return ResponseEntity.ok(response);
    }

    // 🔥 DELETE PRODUCT
    @Operation(summary = "Eliminar producto", description = "Elimina lógicamente o físicamente un producto por su ID (Solo Administradores)")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // 🔥 GET PRODUCTS BY CATEGORY
    @Operation(summary = "Obtener productos por categoría", description = "Filtra la lista de productos por el ID de su categoría")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                service.getByCategory(categoryId)
        );
    }
}