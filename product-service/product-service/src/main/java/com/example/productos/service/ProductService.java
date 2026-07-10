package com.example.productos.service;

import com.example.productos.client.CategoryClient;
import com.example.productos.client.InventoryClient;
import com.example.productos.dto.*;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductoRepository repository;
    private final CategoryClient categoryClient;
    private final InventoryClient inventoryClient;
    public ProductService(ProductoRepository repository,
                          CategoryClient categoryClient, InventoryClient inventoryClient) {
        this.repository = repository;
        this.categoryClient = categoryClient;
        this.inventoryClient = inventoryClient;
    }

    //  CREATE
    public ProductResponseDTO createFullProduct(
            ProductFullRequestDTO request
    ) {

        Producto product =
                new Producto();

        product.setName(request.getName());

        product.setDescription(
                request.getDescription()
        );

        product.setPrice(request.getPrice());

        product.setCategoryId(
                request.getCategoryId()
        );

        product.setActive(true);

        Producto saved =
                repository.save(product);

        InventoryRequestDTO inventory =
                new InventoryRequestDTO();

        inventory.setProductId(
                saved.getId()
        );

        inventory.setStock(
                request.getStock()
        );

        inventoryClient.createInventory(
                inventory
        );

        return mapToResponse(saved);
    }

    //  GET BY ID
    public ProductResponseDTO getById(Long id) {

        Producto product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        return mapToResponse(product);
    }

    // GET ALL
    public List<ProductResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE
    public ProductResponseDTO update(Long id,
                                     ProductRequestDTO request) {

        Producto product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        // validar categoría nuevamente
        categoryClient.getCategory(request.getCategoryId());

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategoryId(request.getCategoryId());

        Producto updated = repository.save(product);

        return mapToResponse(updated);
    }

    // 🔥 DELETE
    public void delete(Long id) {

        Producto product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));

        repository.delete(product);
    }

    // 🔥 GET PRODUCTS BY CATEGORY
    public List<ProductResponseDTO> getByCategory(Long categoryId) {

        return repository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 MAP DTO
    private ProductResponseDTO mapToResponse(Producto product) {

        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategoryId());

        return dto;
    }
}