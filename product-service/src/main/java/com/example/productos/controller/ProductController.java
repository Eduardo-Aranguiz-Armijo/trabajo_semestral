package com.example.productos.controller;

import com.example.productos.dto.ProductFullRequestDTO;
import com.example.productos.dto.ProductRequestDTO;
import com.example.productos.dto.ProductResponseDTO;
import com.example.productos.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // 🔥 CREATE PRODUCT
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(
            @RequestBody ProductFullRequestDTO request) {

        return ResponseEntity.ok(
                service.createFullProduct(request)
        );
    }

    // 🔥 GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }

    // 🔥 GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    // 🔥 UPDATE PRODUCT
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO request) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }

    // 🔥 DELETE PRODUCT
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // 🔥 GET PRODUCTS BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                service.getByCategory(categoryId)
        );
    }
}