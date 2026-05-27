package com.example.catalogos.controller;

import com.example.catalogos.dto.CategoryRequestDTO;
import com.example.catalogos.dto.CategoryResponseDTO;
import com.example.catalogos.dto.ProductResponseDTO;
import com.example.catalogos.service.CatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CatalogoController {

    private final CatalogoService service;

    public CatalogoController(CatalogoService service) {
        this.service = service;
    }

    // 🔥 CREATE CATEGORY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(
            @RequestBody CategoryRequestDTO request) {

        return ResponseEntity.ok(
                service.create(request)
        );
    }

    // 🔥 GET CATEGORY BY ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    // 🔥 GET ALL CATEGORIES
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // 🔥 UPDATE CATEGORY
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO request) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }

    // 🔥 DELETE CATEGORY
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // 🔥 GET PRODUCTS BY CATEGORY
    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getProductsByCategory(id)
        );
    }
}