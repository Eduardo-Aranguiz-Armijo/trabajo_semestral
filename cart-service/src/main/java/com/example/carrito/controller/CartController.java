package com.example.carrito.controller;

import com.example.carrito.dto.CartItemRequestDTO;
import com.example.carrito.dto.CartItemResponseDTO;
import com.example.carrito.dto.CartRequestDTO;
import com.example.carrito.dto.CartResponseDTO;
import com.example.carrito.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    // =========================================
    // CART CRUD
    // =========================================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart() {

        return ResponseEntity.ok(
                service.createCart()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CartResponseDTO>>
    getAllCarts() {

        return ResponseEntity.ok(
                service.getAllCarts()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO>
    getCartById(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.getCartById(id)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO>
    updateCart(
            @PathVariable Long id,
            @RequestBody CartRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.updateCart(id, request)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteCart(@PathVariable Long id) {

        service.deleteCart(id);

        return ResponseEntity.noContent().build();
    }

    // =========================================
    // CART ITEM CRUD
    // =========================================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/items")
    public ResponseEntity<CartItemResponseDTO>
    createItem(
            @RequestBody CartItemRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.createItem(request)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemResponseDTO>>
    getItemsByCart(
            @PathVariable Long cartId
    ) {

        return ResponseEntity.ok(
                service.getItemsByCart(cartId)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/items/{id}")
    public ResponseEntity<CartItemResponseDTO>
    updateItem(
            @PathVariable Long id,
            @RequestBody CartItemRequestDTO request
    ) {

        return ResponseEntity.ok(
                service.updateItem(id, request)
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void>
    deleteItem(@PathVariable Long id) {

        service.deleteItem(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponseDTO>>
    getMyItems() {

        return ResponseEntity.ok(
                service.getMyItems()
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/total")
    public ResponseEntity<Double> getMyTotal() {

        return ResponseEntity.ok(
                service.getMyTotal()
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<CartResponseDTO>
    getMyCart() {

        return ResponseEntity.ok(
                service.getMyCart()
        );
    }
}