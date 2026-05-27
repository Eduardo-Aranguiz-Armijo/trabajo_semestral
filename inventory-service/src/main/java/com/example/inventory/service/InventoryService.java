package com.example.inventory.service;

import com.example.inventory.client.ProductClient;
import com.example.inventory.dto.InventoryRequestDTO;
import com.example.inventory.dto.InventoryResponseDTO;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {
    private final InventoryRepository repository;
    private final ProductClient productClient;

    public InventoryService(
            InventoryRepository repository,
            ProductClient productClient
    ) {

        this.repository = repository;
        this.productClient = productClient;
    }

    // CREATE

    public InventoryResponseDTO create(
            InventoryRequestDTO request
    ) {

        // VALIDAR PRODUCTO EXISTENTE
        productClient.getProduct(
                request.getProductId()
        );

        // Si ya existe inventario para este producto, actualizar stock (evita 409 al crear productos)
        return repository.findByProductId(request.getProductId())
                .map(existing -> {
                    existing.setStock(request.getStock());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return map(repository.save(existing));
                })
                .orElseGet(() -> {
                    Inventory inventory = new Inventory();
                    inventory.setProductId(request.getProductId());
                    inventory.setStock(request.getStock());
                    inventory.setUpdatedAt(LocalDateTime.now());
                    return map(repository.save(inventory));
                });
    }

    // GET BY PRODUCT

    public InventoryResponseDTO getByProduct(
            Long productId
    ) {

        Inventory inventory =
                repository.findByProductId(
                        productId
                ).orElseThrow();

        return map(inventory);
    }

    // UPDATE STOCK

    public InventoryResponseDTO updateStock(
            Long productId,
            Integer stock
    ) {

        Inventory inventory =
                repository.findByProductId(
                        productId
                ).orElseThrow();

        inventory.setStock(stock);

        inventory.setUpdatedAt(
                LocalDateTime.now()
        );

        Inventory updated =
                repository.save(inventory);

        return map(updated);
    }

    // DECREASE STOCK

    public InventoryResponseDTO decreaseStock(
            Long productId,
            Integer quantity
    ) {

        Inventory inventory =
                repository.findByProductId(
                        productId
                ).orElseThrow();

        if (inventory.getStock()
                < quantity) {

            throw new RuntimeException(
                    "Insufficient stock"
            );
        }

        inventory.setStock(
                inventory.getStock()
                        - quantity
        );

        inventory.setUpdatedAt(
                LocalDateTime.now()
        );

        Inventory updated =
                repository.save(inventory);

        return map(updated);
    }

    // MAPPER

    private InventoryResponseDTO map(
            Inventory inventory
    ) {

        InventoryResponseDTO dto =
                new InventoryResponseDTO();

        dto.setId(inventory.getId());
        dto.setProductId(
                inventory.getProductId()
        );

        dto.setStock(
                inventory.getStock()
        );

        dto.setUpdatedAt(
                inventory.getUpdatedAt()
        );

        return dto;
    }
}
