package com.example.carrito.service;

import com.example.carrito.client.ClienteClient;
import com.example.carrito.client.InventoryClient;
import com.example.carrito.client.ProductClient;
import com.example.carrito.dto.*;
import com.example.carrito.model.Cart;
import com.example.carrito.model.CartItem;
import com.example.carrito.repository.CartItemRepository;
import com.example.carrito.repository.CartRepository;
import com.example.carrito.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final ProductClient productClient;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final HttpServletRequest requestHttp;
    private final JwtService jwtService;
    private final ClienteClient clienteClient;
    private final InventoryClient inventoryClient;
    public CartService(
            ProductClient productClient, CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            HttpServletRequest requestHttp,
            JwtService jwtService,
            ClienteClient clienteClient, InventoryClient inventoryClient
    ) {
        this.productClient = productClient;

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.requestHttp = requestHttp;
        this.jwtService = jwtService;
        this.clienteClient = clienteClient;
        this.inventoryClient = inventoryClient;
    }

    // =========================================
    // CART CRUD
    // =========================================

    public CartResponseDTO createCart() {
        String token =
                requestHttp.getHeader("Authorization");

        Long userId =
                jwtService.extractUserId(token);

        System.out.println(userId);

        ClienteResponseDTO cliente =

                clienteClient.getByUserId(userId);



        Cart cart = new Cart();

        cart.setClienteId(cliente.getId());
        cart.setEstado("ACTIVE");

        Cart saved =
                cartRepository.save(cart);

        return mapCart(saved);
    }

    public CartResponseDTO getMyCart() {

        String token =
                requestHttp.getHeader("Authorization");

        Long userId =
                jwtService.extractUserId(token);

        ClienteResponseDTO cliente =
                clienteClient.getByUserId(userId);

        Cart cart =
                cartRepository
                        .findByClienteIdAndEstado(
                                cliente.getId(),
                                "ACTIVE"
                        )
                        .orElseThrow();

        return mapCart(cart);
    }
    public List<CartResponseDTO> getAllCarts() {

        return cartRepository.findAll()
                .stream()
                .map(this::mapCart)
                .toList();
    }

    public CartResponseDTO getCartById(Long id) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow();

        return mapCart(cart);
    }

    public CartResponseDTO updateCart(
            Long id,
            CartRequestDTO request
    ) {

        Cart cart = cartRepository.findById(id)
                .orElseThrow();

        cart.setEstado(request.getEstado());

        Cart updated =
                cartRepository.save(cart);

        return mapCart(updated);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    // =========================================
    // CART ITEM CRUD
    // =========================================

    public CartItemResponseDTO createItem(
            CartItemRequestDTO request
    ) {

        String token =
                requestHttp.getHeader("Authorization");

        Long userId =
                jwtService.extractUserId(token);

        ClienteResponseDTO cliente =
                clienteClient.getByUserId(userId);

        Cart cart =
                cartRepository
                        .findByClienteIdAndEstado(
                                cliente.getId(),
                                "ACTIVE"
                        )
                        .orElseThrow();

        // VALIDAR PRODUCTO
        productClient.getProduct(
                request.getProductId()
        );

        // VALIDAR STOCK
        InventoryResponseDTO inventory =
                inventoryClient.getInventory(
                        request.getProductId()
                );

        if (request.getCantidad()
                > inventory.getStock()) {

            throw new RuntimeException(
                    "Insufficient stock"
            );
        }

        CartItem item =
                new CartItem();

        item.setCartId(cart.getId());

        item.setProductId(
                request.getProductId()
        );

        item.setCantidad(
                request.getCantidad()
        );

        CartItem saved =
                cartItemRepository.save(item);

        return mapItem(saved);
    }


    public List<CartItemResponseDTO> getItemsByCart(
            Long cartId
    ) {

        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(this::mapItem)
                .toList();
    }

    public CartItemResponseDTO updateItem(
            Long id,
            CartItemRequestDTO request
    ) {

        CartItem item =
                cartItemRepository.findById(id)
                        .orElseThrow();

        item.setCantidad(request.getCantidad());

        CartItem updated =
                cartItemRepository.save(item);

        return mapItem(updated);
    }

    public void deleteItem(Long id) {
        cartItemRepository.deleteById(id);
    }


    public List<CartItemResponseDTO> getMyItems() {

        String token =
                requestHttp.getHeader("Authorization");

        Long userId =
                jwtService.extractUserId(token);

        ClienteResponseDTO cliente =
                clienteClient.getByUserId(userId);

        Cart cart =
                cartRepository
                        .findByClienteIdAndEstado(
                                cliente.getId(),
                                "ACTIVE"
                        )
                        .orElseThrow();

        return cartItemRepository
                .findByCartId(cart.getId())
                .stream()
                .map(this::mapItem)
                .toList();
    }

    public Double getMyTotal() {

        String token =
                requestHttp.getHeader("Authorization");

        Long userId =
                jwtService.extractUserId(token);

        ClienteResponseDTO cliente =
                clienteClient.getByUserId(userId);

        Cart cart =
                cartRepository
                        .findByClienteIdAndEstado(
                                cliente.getId(),
                                "ACTIVE"
                        )
                        .orElseThrow();

        List<CartItem> items =
                cartItemRepository
                        .findByCartId(cart.getId());

        Double total = 0.0;

        for (CartItem item : items) {

            ProductResponseDTO product =
                    productClient.getProduct(
                            item.getProductId()
                    );

            total +=
                    product.getPrice()
                            * item.getCantidad();
        }

        return total;
    }
    // =========================================
    // MAPPERS
    // =========================================

    private CartResponseDTO mapCart(Cart cart) {

        CartResponseDTO dto =
                new CartResponseDTO();

        dto.setId(cart.getId());
        dto.setClienteId(cart.getClienteId());
        dto.setEstado(cart.getEstado());

        return dto;
    }

    private CartItemResponseDTO mapItem(
            CartItem item
    ) {

        CartItemResponseDTO dto =
                new CartItemResponseDTO();

        dto.setId(item.getId());
        dto.setCartId(item.getCartId());
        dto.setProductId(item.getProductId());
        dto.setCantidad(item.getCantidad());

        return dto;
    }
}