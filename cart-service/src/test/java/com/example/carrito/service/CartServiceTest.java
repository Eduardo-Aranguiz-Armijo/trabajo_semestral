package com.example.carrito.service;

import com.example.carrito.client.ClienteClient;
import com.example.carrito.client.ProductClient;
import com.example.carrito.dto.CartRequestDTO;
import com.example.carrito.dto.CartResponseDTO;
import com.example.carrito.dto.ClientResponseDTO;
import com.example.carrito.dto.ProductResponseDTO;
import com.example.carrito.exception.exceptions.ActiveCartAlreadyExistsException;
import com.example.carrito.exception.exceptions.CartNotFoundException;
import com.example.carrito.model.Cart;
import com.example.carrito.model.CartItem;
import com.example.carrito.repository.CartItemRepository;
import com.example.carrito.repository.CartRepository;
import com.example.carrito.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private HttpServletRequest requestHttp;
    @Mock
    private JwtService jwtService;
    @Mock
    private ClienteClient clienteClient;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductClient productClient;

    @InjectMocks
    private CartService cartService;

    private ClientResponseDTO dummyClient;
    private Cart dummyCart;
    private CartItem dummyItem;
    private ProductResponseDTO dummyProduct;

    @BeforeEach
    void setUp() {
        dummyClient = new ClientResponseDTO();
        dummyClient.setId(10L);

        dummyCart = new Cart();
        dummyCart.setId(1L);
        dummyCart.setClientId(10L);
        dummyCart.setStatus("ACTIVE");

        dummyItem = new CartItem();
        dummyItem.setId(100L);
        dummyItem.setCartId(1L);
        dummyItem.setProductId(5L);
        dummyItem.setStock(2);

        dummyProduct = new ProductResponseDTO();
        dummyProduct.setId(5L);
        dummyProduct.setPrice(50.0);
    }

    @Test
    void testCreateCart_Success() {
        when(requestHttp.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUserId("Bearer token")).thenReturn(99L);
        when(clienteClient.getByUserId(99L)).thenReturn(dummyClient);
        when(cartRepository.findAll()).thenReturn(List.of()); // No active carts
        when(cartRepository.save(any(Cart.class))).thenReturn(dummyCart);

        CartResponseDTO response = cartService.createCart();

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("ACTIVE", response.getStatus());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testCreateCart_AlreadyExists() {
        when(requestHttp.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUserId("Bearer token")).thenReturn(99L);
        when(clienteClient.getByUserId(99L)).thenReturn(dummyClient);
        when(cartRepository.findAll()).thenReturn(List.of(dummyCart)); // Active cart exists!

        assertThrows(ActiveCartAlreadyExistsException.class, () -> {
            cartService.createCart();
        });

        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    void testGetMyTotal_Success() {
        when(requestHttp.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtService.extractUserId("Bearer token")).thenReturn(99L);
        when(clienteClient.getByUserId(99L)).thenReturn(dummyClient);
        when(cartRepository.findAll()).thenReturn(List.of(dummyCart));
        when(cartItemRepository.findByCartId(1L)).thenReturn(List.of(dummyItem));
        when(productClient.getProduct(5L)).thenReturn(dummyProduct);

        Double total = cartService.getMyTotal();

        assertEquals(100.0, total); // 50.0 * 2
    }

    @Test
    void testGetCartById_NotFound() {
        when(cartRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> {
            cartService.getCartById(99L);
        });
    }
}
