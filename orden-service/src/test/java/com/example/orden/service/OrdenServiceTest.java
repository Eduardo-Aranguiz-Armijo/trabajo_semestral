package com.example.orden.service;

import com.example.orden.client.CartClient;
import com.example.orden.client.ClientClient;
import com.example.orden.client.InventoryClient;
import com.example.orden.client.ProductClient;
import com.example.orden.dto.CartItemResponseDTO;
import com.example.orden.dto.CartResponseDTO;
import com.example.orden.dto.OrderResponseDTO;
import com.example.orden.dto.ProductResponseDTO;
import com.example.orden.exception.exceptions.EmptyCartException;
import com.example.orden.exception.exceptions.OrderNotFoundException;
import com.example.orden.model.Order;
import com.example.orden.repository.OrderItemRepository;
import com.example.orden.repository.OrderRepository;
import com.example.orden.security.jwt.JwtService;
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
class OrdenServiceTest {

    @Mock
    private OrderRepository repository;
    @Mock
    private OrderItemRepository itemRepository;
    @Mock
    private CartClient cartClient;
    @Mock
    private ProductClient productClient;
    @Mock
    private InventoryClient inventoryClient;
    @Mock
    private HttpServletRequest requestHttp;
    @Mock
    private ClientClient clientClient;
    @Mock
    private JwtService jwtService;

    @InjectMocks
    private OrdenService ordenService;

    private Order dummyOrder;
    private CartResponseDTO dummyCart;
    private CartItemResponseDTO dummyCartItem;
    private ProductResponseDTO dummyProduct;

    @BeforeEach
    void setUp() {
        dummyOrder = new Order();
        dummyOrder.setId(1L);
        dummyOrder.setClientId(2L);
        dummyOrder.setCartId(3L);
        dummyOrder.setStatus("PENDING_PAYMENT");
        dummyOrder.setTotal(100.0);

        dummyCart = new CartResponseDTO();
        dummyCart.setId(3L);
        dummyCart.setClientId(2L);
        dummyCart.setStatus("ACTIVE");

        dummyCartItem = new CartItemResponseDTO();
        dummyCartItem.setId(1L);
        dummyCartItem.setCartId(3L);
        dummyCartItem.setProductId(10L);
        dummyCartItem.setStock(2);

        dummyProduct = new ProductResponseDTO();
        dummyProduct.setId(10L);
        dummyProduct.setPrice(50.0);
    }

    @Test
    void testCreateOrder_Success() {
        // Arrange
        when(cartClient.getMyCart()).thenReturn(dummyCart);
        when(cartClient.getMyItems()).thenReturn(List.of(dummyCartItem));
        when(productClient.getProduct(10L)).thenReturn(dummyProduct);
        when(repository.save(any(Order.class))).thenReturn(dummyOrder);

        // Act
        OrderResponseDTO response = ordenService.createOrder();

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(100.0, response.getTotal());
        assertEquals("PENDING_PAYMENT", response.getStatus());

        verify(inventoryClient, times(1)).decreaseStock(10L, 2);
        verify(repository, times(1)).save(any(Order.class));
        verify(cartClient, times(1)).updateCartStatus(3L, "CHECKOUT");
    }

    @Test
    void testCreateOrder_EmptyCart() {
        // Arrange
        when(cartClient.getMyCart()).thenReturn(dummyCart);
        when(cartClient.getMyItems()).thenReturn(List.of()); // Empty list

        // Act & Assert
        assertThrows(EmptyCartException.class, () -> {
            ordenService.createOrder();
        });

        // Verificamos que no se intentó guardar nada
        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void testGetById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(dummyOrder));
        
        OrderResponseDTO response = ordenService.getById(1L);
        
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void testGetById_NotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(OrderNotFoundException.class, () -> {
            ordenService.getById(99L);
        });
    }

    @Test
    void testUpdateStatus_ToPaid() {
        when(repository.findById(1L)).thenReturn(Optional.of(dummyOrder));
        
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setCartId(3L);
        updatedOrder.setStatus("PAID");
        when(repository.save(any(Order.class))).thenReturn(updatedOrder);

        OrderResponseDTO response = ordenService.updateStatus(1L, "PAID");

        assertEquals("PAID", response.getStatus());
        verify(cartClient, times(1)).updateCartStatus(3L, "READY");
    }
}
