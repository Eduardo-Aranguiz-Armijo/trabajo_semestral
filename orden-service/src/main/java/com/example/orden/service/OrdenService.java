package com.example.orden.service;

import com.example.orden.client.CartClient;
import com.example.orden.client.ClientClient;
import com.example.orden.client.InventoryClient;
import com.example.orden.client.ProductClient;
import com.example.orden.dto.*;
import com.example.orden.exception.exceptions.*;
import com.example.orden.model.Order;
import com.example.orden.model.OrderItem;
import com.example.orden.repository.OrderRepository;
import com.example.orden.repository.OrderItemRepository;
import com.example.orden.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenService {

    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final HttpServletRequest requestHttp;
    private final ClientClient clientClient;
    private final JwtService jwtService;

    public OrdenService(
            OrderRepository repository, OrderItemRepository itemRepository,
            CartClient cartClient,
            ProductClient productClient,
            InventoryClient inventoryClient,
            HttpServletRequest requestHttp,
            ClientClient clientClient,
            JwtService jwtService
    ) {

        this.repository = repository;
        this.itemRepository = itemRepository;

        this.cartClient = cartClient;

        this.productClient = productClient;

        this.inventoryClient = inventoryClient;

        this.requestHttp = requestHttp;

        this.clientClient = clientClient;

        this.jwtService = jwtService;
    }

    // =========================================
    // CREATE ORDER
    // =========================================

    public OrderResponseDTO createOrder() {

        CartResponseDTO cart = getActiveCart();
        List<CartItemResponseDTO> items = cartClient.getMyItems();
        validateCartItems(items);
        Double total = calculateTotal(items);
        decreaseInventory(items);
        Order order = buildOrder(cart, total);

        Order saved = repository.save(order);
        for (CartItemResponseDTO item : items) {

            ProductResponseDTO product =
                    productClient.getProduct(item.getProductId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getStock());
            orderItem.setPrice(product.getPrice());

            itemRepository.save(orderItem);
        }
        // CART -> CHECKOUT
        cartClient.updateCartStatus(cart.getId(), "CHECKOUT");

        return map(saved);
    }

    // =========================================
    // GET MY ORDERS
    // =========================================

    public List<OrderResponseDTO> getMyOrders() {

        Long clientId = getCurrentClientId();

        return repository
                .findByClientId(clientId)
                .stream()
                .map(this::map)
                .toList();
    }

    // =========================================
    // GET ORDER BY ID
    // =========================================

    public OrderResponseDTO getById(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return map(order);
    }

    // =========================================
    // GET ALL ORDERS
    // =========================================

    public List<OrderResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    // =========================================
    // UPDATE STATUS
    // =========================================

    public OrderResponseDTO updateStatus(Long id, String status) {

        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        order.setStatus(status.toUpperCase());
        Order updated = repository.save(order);

        // IF ORDER IS PAID
        if (updated.getStatus().equals("PAID")) {
            cartClient.updateCartStatus(updated.getCartId(), "READY");
        }
        // IF ORDER IS CANCELLED
        if (updated.getStatus().equals("CANCELLED")) {

            cartClient.updateCartStatus(updated.getCartId(), "CANCELLED");
        }
        return map(updated);
    }

    // =========================================
    // CANCEL MY ORDER
    // =========================================

    public OrderResponseDTO cancelMyOrder(
            Long id
    ) {

        Long clientId = getCurrentClientId();
        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (!order.getClientId().equals(clientId)) {
            throw new UnauthorizedOrderAccessException("You cannot cancel this order");
        }
        if (order.getStatus().equals("PAID")) {

            throw new InvalidOrderStatusException(
                    "Paid orders cannot be cancelled"
            );
        }

        order.setStatus("CANCELLED");
        Order updated = repository.save(order);
        cartClient.updateCartStatus(updated.getCartId(), "ACTIVE");
        restoreStock(order.getId());
        return map(updated);
    }

    // =========================================
    // DELETE ORDER
    // =========================================

    public void deleteOrder(Long id) {

        Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));

        repository.delete(order);
    }

    // =========================================
    // PRIVATE METHODS
    // =========================================

    private Long getCurrentClientId() {

        String token = requestHttp.getHeader("Authorization");
        Long userId = jwtService.extractUserId(token);
        ClientResponseDTO client = clientClient.getByUserId(userId);
        return client.getId();
    }

    private void restoreStock(Long orderId) {

        List<OrderItem> items = itemRepository.findByOrderId(orderId);
        for (OrderItem item : items) {
            inventoryClient.increaseStock(
                    item.getProductId(),
                    item.getQuantity()
            );
        }
    }


    private CartResponseDTO getActiveCart() {
        CartResponseDTO cart = cartClient.getMyCart();
        validateCartEditable(cart);
        return cart;
    }

    private void validateCartEditable(
            CartResponseDTO cart
    ) {
        if (!cart.getStatus().equals("ACTIVE")) {
            throw new InvalidCartStateException(
                    "Cart is not editable"
            );
        }
    }

    private void validateCartItems(List<CartItemResponseDTO> items) {

        if (items.isEmpty()) {
            throw new EmptyCartException(
                    "Cart is empty");
        }
    }

    private Double calculateTotal(List<CartItemResponseDTO> items) {
        Double total = 0.0;
        for (CartItemResponseDTO item : items) {
            ProductResponseDTO product = productClient.getProduct(item.getProductId());
            total += product.getPrice() * item.getStock();
        }

        return total;
    }

    private void decreaseInventory(List<CartItemResponseDTO> items) {

        for (CartItemResponseDTO item : items) {
            inventoryClient.decreaseStock(item.getProductId(), item.getStock());
        }
    }

    private Order buildOrder(CartResponseDTO cart, Double total) {

        Order order = new Order();
        order.setClientId(cart.getClientId());
        order.setCartId(cart.getId());
        order.setStatus("PENDING_PAYMENT");
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(total);
        return order;
    }

    // =========================================
    // MAPPER
    // =========================================

    private OrderResponseDTO map(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setClientId(order.getClientId());
        dto.setCartId(order.getCartId());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setTotal(order.getTotal());
        return dto;
    }
}