package com.example.carrito.service;

import com.example.carrito.client.ClienteClient;
import com.example.carrito.client.ProductClient;
import com.example.carrito.dto.*;
import com.example.carrito.exception.exceptions.ActiveCartAlreadyExistsException;
import com.example.carrito.exception.exceptions.CartNotFoundException;
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

    private final CartRepository cartRepository;
    private final HttpServletRequest requestHttp;
    private final JwtService jwtService;
    private final ClienteClient clienteClient;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;
    //inyeccion manual
    public CartService(
            CartRepository cartRepository,
            HttpServletRequest requestHttp,
            JwtService jwtService,
            ClienteClient clienteClient, CartItemRepository cartItemRepository, ProductClient productClient)
    {
        this.cartRepository = cartRepository;
        this.requestHttp = requestHttp;
        this.jwtService = jwtService;
        this.clienteClient = clienteClient;
        this.cartItemRepository = cartItemRepository;
        this.productClient = productClient;
    }

    // =========================================
    // CREATE CART
    // =========================================

    public CartResponseDTO createCart() {
        ClientResponseDTO client = getAuthenticatedClient();
        validateCartCreation(client.getId());
        Cart cart = new Cart();
        cart.setClientId(client.getId());
        cart.setStatus("ACTIVE");
        Cart saved = cartRepository.save(cart);
        return map(saved);
    }

    // =========================================
    // GET MY CART
    // =========================================

    public CartResponseDTO getMyCart() {
        ClientResponseDTO client = getAuthenticatedClient();
        Cart cart = findEditableCart(client.getId());
        return map(cart);
    }

    // =========================================
    // CANCEL MY CART
    // =========================================

    public void cancelMyCart() {

        ClientResponseDTO client = getAuthenticatedClient();
        Cart cart = findEditableCart(client.getId());
        cart.setStatus("CANCELLED");
        cartRepository.save(cart);
    }

    // =========================================
    // GET TOTAL
    // =========================================

    public Double getMyTotal() {

        ClientResponseDTO client =
                getAuthenticatedClient();
        Cart cart = findEditableCart(client.getId());
        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        Double total = 0.0;

        for (CartItem item : items) {
            ProductResponseDTO product = productClient.getProduct(item.getProductId());

            total += product.getPrice() * item.getStock();}
        double totalRound = Math.round(total);
        return totalRound;
    }

    // =========================================
    // ADMIN METHODS
    // =========================================

    public List<CartResponseDTO> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public CartResponseDTO getCartById(Long id) {

        return map(findCartById(id));
    }

    public CartResponseDTO updateCart(Long id, CartRequestDTO request) {

        Cart cart = findCartById(id);
        cart.setStatus(request.getStatus().toUpperCase());
        Cart updated = cartRepository.save(cart);
        return map(updated);
    }

    public void deleteCart(Long id) {

        Cart cart = findCartById(id);
        cart.setStatus("CANCELLED");
        cartRepository.save(cart);
    }

    // =========================================
    // PRIVATE METHODS
    // =========================================

    private ClientResponseDTO
    getAuthenticatedClient() {

        String token = requestHttp.getHeader("Authorization");
        Long userId = jwtService.extractUserId(token);
        return clienteClient.getByUserId(userId);
    }

    private void validateCartCreation(Long clientId) {

        boolean exists = cartRepository.findAll()
                        .stream()
                        .anyMatch(cart -> cart.getClientId().equals(clientId) &&
                                (cart.getStatus().equals("ACTIVE") || cart.getStatus().equals("CHECKOUT")));

        if (exists) {
            throw new ActiveCartAlreadyExistsException("You already have an active cart");
        }
    }

    private Cart findEditableCart(Long clientId) {

        return cartRepository.findAll()
                .stream()
                .filter(cart -> cart.getClientId().equals(clientId))
                .filter(cart -> cart.getStatus().equals("ACTIVE"))
                .findFirst()
                .orElseThrow(() -> new CartNotFoundException("Active cart not found"));
    }

    private Cart findCartById(Long id) {

        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    private CartResponseDTO map(Cart cart) {

        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());
        dto.setClientId(cart.getClientId());
        dto.setStatus(cart.getStatus());

        return dto;
    }
}