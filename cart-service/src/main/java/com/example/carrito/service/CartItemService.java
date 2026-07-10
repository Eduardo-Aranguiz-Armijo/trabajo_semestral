package com.example.carrito.service;

import com.example.carrito.client.ClienteClient;
import com.example.carrito.client.InventoryClient;
import com.example.carrito.client.ProductClient;
import com.example.carrito.dto.CartItemRequestDTO;
import com.example.carrito.dto.CartItemResponseDTO;
import com.example.carrito.dto.ClientResponseDTO;
import com.example.carrito.dto.InventoryResponseDTO;
import com.example.carrito.exception.exceptions.CartItemNotFoundException;
import com.example.carrito.exception.exceptions.CartNotFoundException;
import com.example.carrito.exception.exceptions.InsufficientStockException;
import com.example.carrito.model.Cart;
import com.example.carrito.model.CartItem;
import com.example.carrito.repository.CartItemRepository;
import com.example.carrito.repository.CartRepository;
import com.example.carrito.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final ProductClient productClient;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final InventoryClient inventoryClient;
    private final HttpServletRequest requestHttp;
    private final JwtService jwtService;
    private final ClienteClient clienteClient;
    //inyeccion manual
    public CartItemService(
            ProductClient productClient,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            InventoryClient inventoryClient,
            HttpServletRequest requestHttp,
            JwtService jwtService,
            ClienteClient clienteClient
    ) {
        this.productClient = productClient;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.inventoryClient = inventoryClient;
        this.requestHttp = requestHttp;
        this.jwtService = jwtService;
        this.clienteClient = clienteClient;
    }

    // =========================================
    // CREATE ITEM
    // =========================================

    public CartItemResponseDTO createItem(CartItemRequestDTO request) {

        ClientResponseDTO client = getAuthenticatedClient();
        Cart cart = getActiveCart(client.getId());
        validateProduct(request.getProductId());
        validateStock(request.getProductId(), request.getStock());

        Optional<CartItem> existing = cartItemRepository.findByCartIdAndProductId(
                cart.getId(), request.getProductId());

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setStock(item.getStock() + request.getStock());
            return map(cartItemRepository.save(item));
        }

        CartItem item = new CartItem();
        item.setCartId(cart.getId());
        item.setProductId(request.getProductId());
        item.setStock(request.getStock());
        return map(cartItemRepository.save(item));
    }

    // =========================================
    // GET MY ITEMS
    // =========================================

    public List<CartItemResponseDTO> getMyItems() {
        ClientResponseDTO client = getAuthenticatedClient();
        Cart cart = getActiveCart(client.getId());
        return cartItemRepository
                .findByCartId(cart.getId())
                .stream()
                .map(this::map)
                .toList();
    }

    // =========================================
    // UPDATE ITEM
    // =========================================

    public CartItemResponseDTO updateItem(Long id, CartItemRequestDTO request) {
        CartItem item = findItemById(id);
        validateStock(item.getProductId(), request.getStock());
        item.setStock(request.getStock());
        return map(cartItemRepository.save(item));
    }

    // =========================================
    // DELETE ITEM
    // =========================================

    public void deleteItem(Long id) {
        CartItem item = findItemById(id);
        cartItemRepository.delete(item);
    }

    // =========================================
    // ADMIN - ITEMS BY CART
    // =========================================

    public List<CartItemResponseDTO> getItemsByCart(Long cartId) {

        return cartItemRepository
                .findByCartId(cartId)
                .stream()
                .map(this::map)
                .toList();
    }

    // =========================================
    // PRIVATE METHODS
    // =========================================

    private ClientResponseDTO getAuthenticatedClient() {

        String token = requestHttp.getHeader("Authorization");
        Long userId = jwtService.extractUserId(token);
        return clienteClient.getByUserId(userId);
    }

    private Cart getActiveCart(Long clientId) {

        return cartRepository.findAll()
                .stream()
                .filter(cart -> cart.getClientId().equals(clientId))
                .filter(cart -> cart.getStatus().equals("ACTIVE"))
                .findFirst()
                .orElseThrow(() -> new CartNotFoundException("Active cart not found"));
    }

    private void validateProduct(Long productId) {
        productClient.getProduct(productId);
    }

    private void validateStock(Long productId, Integer quantity) {

        InventoryResponseDTO inventory = inventoryClient.getInventory(productId);
        if (quantity > inventory.getStock()) {
            throw new InsufficientStockException("Insufficient stock");
        }
    }

    private CartItem findItemById(Long id) {

        return cartItemRepository
                .findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
    }

    private CartItemResponseDTO map(CartItem item) {

        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setId(item.getId());
        dto.setCartId(item.getCartId());
        dto.setProductId(item.getProductId());
        dto.setStock(item.getStock());

        return dto;
    }
}