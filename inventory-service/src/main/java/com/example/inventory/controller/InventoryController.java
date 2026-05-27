package com.example.inventory.controller;

import com.example.inventory.dto.InventoryRequestDTO;
import com.example.inventory.dto.InventoryResponseDTO;
import com.example.inventory.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(
            InventoryService service
    ) {

        this.service = service;
    }

    // CREATE

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<InventoryResponseDTO>
    create(
            @RequestBody
            InventoryRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.create(request)
        );
    }

    // GET BY PRODUCT

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryResponseDTO>
    getByProduct(
            @PathVariable Long productId
    ) {

        return ResponseEntity.ok(
                service.getByProduct(productId)
        );
    }

    // UPDATE STOCK

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{productId}")
    public ResponseEntity<InventoryResponseDTO>
    updateStock(
            @PathVariable Long productId,
            @RequestParam Integer stock
    ) {

        return ResponseEntity.ok(
                service.updateStock(
                        productId,
                        stock
                )
        );
    }

    // DECREASE STOCK

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{productId}/decrease")
    public ResponseEntity<InventoryResponseDTO>
    decreaseStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {

        return ResponseEntity.ok(
                service.decreaseStock(
                        productId,
                        quantity
                )
        );
    }
}
