package com.example.orden.service;

import com.example.orden.client.CartClient;
import com.example.orden.client.InventoryClient;
import com.example.orden.client.ProductClient;
import com.example.orden.dto.CartItemResponseDTO;
import com.example.orden.dto.CartResponseDTO;
import com.example.orden.dto.OrderResponseDTO;
import com.example.orden.dto.ProductResponseDTO;
import com.example.orden.model.Orden;
import com.example.orden.repository.OrdenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenService {
    private final OrdenRepository repository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public OrdenService(
            OrdenRepository repository,
            CartClient cartClient,
            ProductClient productClient, InventoryClient inventoryClient
    ) {

        this.repository = repository;
        this.cartClient = cartClient;
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
    }

    public OrderResponseDTO createOrder() {

        CartResponseDTO cart =
                cartClient.getMyCart();

        List<CartItemResponseDTO> items =
                cartClient.getMyItems();

        Double total = 0.0;

        for (CartItemResponseDTO item : items) {

            ProductResponseDTO product =
                    productClient.getProduct(
                            item.getProductId()
                    );

            total +=
                    product.getPrice()
                            * item.getCantidad();

            inventoryClient.decreaseStock(
                    item.getProductId(),
                    item.getCantidad()
            );
        }

        Orden orden = new Orden();

        orden.setClienteId(cart.getClienteId());
        orden.setCartId(cart.getId());
        orden.setEstado("PENDING");
        orden.setCreatedAt(LocalDateTime.now());
        orden.setTotal(total);

        Orden saved =
                repository.save(orden);

        return map(saved);
    }

    public OrderResponseDTO updateStatus(
            Long id,
            String estado
    ) {

        Orden order =
                repository.findById(id)
                        .orElseThrow();

        order.setEstado(estado);

        Orden updated =
                repository.save(order);

        return map(updated);
    }

    public OrderResponseDTO getById(Long id) {

        Orden orden =
                repository.findById(id)
                        .orElseThrow();

        return map(orden);
    }

    private OrderResponseDTO map(
            Orden orden
    ) {

        OrderResponseDTO dto =
                new OrderResponseDTO();

        dto.setId(orden.getId());
        dto.setClienteId(orden.getClienteId());
        dto.setCartId(orden.getCartId());
        dto.setEstado(orden.getEstado());
        dto.setCreatedAt(orden.getCreatedAt());
        dto.setTotal(orden.getTotal());

        return dto;
    }
}
