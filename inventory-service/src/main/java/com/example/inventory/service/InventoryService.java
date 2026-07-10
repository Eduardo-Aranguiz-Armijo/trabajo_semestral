package com.example.inventory.service;

import com.example.inventory.client.ProductClient;
import com.example.inventory.dto.InventoryRequestDTO;
import com.example.inventory.dto.InventoryResponseDTO;
import com.example.inventory.exception.InsufficientStockException;
import com.example.inventory.exception.InventoryAlreadyExistsException;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {
    //inyeccion manual
    private final InventoryRepository repository;
    private final ProductClient productClient;

    public InventoryService(InventoryRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    // CREATE

    public InventoryResponseDTO create(InventoryRequestDTO request) {

        validateProduct(request.getProductId());

        validateDuplicate(request.getProductId());

        Inventory inventory = buildInventory(request);

        Inventory saved = repository.save(inventory);
        return map(saved);
    }

    // GET BY PRODUCT

    public InventoryResponseDTO getByProduct(Long productId) {

        Inventory inventory = findInventory(productId);
        return map(inventory);
    }

    // UPDATE STOCK

    public InventoryResponseDTO updateStock(Long productId, Integer stock) {

        Inventory inventory = findInventory(productId);

        inventory.setStock(stock);

        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory updated = repository.save(inventory);
        return map(updated);
    }

    // DECREASE STOCK

    public InventoryResponseDTO decreaseStock(Long productId, Integer quantity) {

        Inventory inventory = findInventory(productId);

        validateStock(inventory, quantity);

        inventory.setStock(inventory.getStock() - quantity);

        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory updated = repository.save(inventory);

        return map(updated);
    }
    // INCREASE STOCK
    public InventoryResponseDTO increaseStock(Long productId, Integer quantity) {

        Inventory inventory = repository.findByProductId(productId).orElseThrow(
                () -> new InventoryNotFoundException("Inventory not found"));

        inventory.setStock(inventory.getStock() + quantity);

        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory updated = repository.save(inventory);

        return map(updated);
    }

    // PRIVATE METHODS

    private Inventory findInventory(Long productId) {

        return repository.findByProductId(productId).orElseThrow(() ->
                new InventoryNotFoundException("Inventory not found"));
    }

    private void validateDuplicate(Long productId) {

        repository.findByProductId(productId).ifPresent(
                i -> {throw new InventoryAlreadyExistsException("Inventory already exists");}
        );
    }

    private void validateStock(Inventory inventory, Integer quantity) {

        if (inventory.getStock() < quantity) {
            throw new InsufficientStockException("Insufficient stock");
        }
    }

    private void validateProduct(Long productId) {

        productClient.getProduct(productId);
    }

    private Inventory buildInventory(InventoryRequestDTO request) {

        Inventory inventory = new Inventory();

        inventory.setProductId(request.getProductId());

        inventory.setStock(request.getStock());

        inventory.setUpdatedAt(LocalDateTime.now());

        return inventory;
    }

    // MAPPER

    private InventoryResponseDTO map(Inventory inventory) {

        InventoryResponseDTO dto = new InventoryResponseDTO();

        dto.setId(inventory.getId());

        dto.setProductId(inventory.getProductId());

        dto.setStock(inventory.getStock());

        dto.setUpdatedAt(inventory.getUpdatedAt());

        return dto;
    }
}