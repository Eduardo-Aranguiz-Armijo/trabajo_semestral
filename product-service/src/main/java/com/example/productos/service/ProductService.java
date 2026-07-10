package com.example.productos.service;

import com.example.productos.client.CategoryClient;
import com.example.productos.client.InventoryClient;
import com.example.productos.dto.*;
import com.example.productos.exception.exceptions.InventoryException;
import com.example.productos.exception.exceptions.ProductNotFoundException;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductoRepository repository;
    private final CategoryClient categoryClient;
    private final InventoryClient inventoryClient;
    //inyeccion manual
    public ProductService(
            ProductoRepository repository,
            CategoryClient categoryClient,
            InventoryClient inventoryClient
    ) {
        this.repository = repository;
        this.categoryClient = categoryClient;
        this.inventoryClient = inventoryClient;
    }

    // CREATE

    public ProductResponseDTO createFullProduct(ProductFullRequestDTO request) {
        validateCategory(request.getCategoryId());
        Producto product = buildProduct(request);
        Producto saved = repository.save(product);
        try {
            InventoryRequestDTO inventory = buildInventoryRequest(saved.getId(), request.getStock());

            inventoryClient.createInventory(inventory);

        } catch (Exception e) {

            repository.delete(saved);

            throw new InventoryException(e.getMessage());
        }

        return mapToResponse(saved);
    }

    // GET BY ID

    public ProductResponseDTO getById(Long id) {
        Producto product = findProduct(id);
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

    public ProductResponseDTO update(Long id, ProductRequestDTO request) {
        Producto product = findProduct(id);
        validateCategory(request.getCategoryId());
        updateFields(product, request);
        Producto updated = repository.save(product);
        return mapToResponse(updated);
    }

    // DELETE

    public void delete(Long id) {
        Producto product = findProduct(id);
        repository.delete(product);
    }

    // GET BY CATEGORY

    public List<ProductResponseDTO> getByCategory(Long categoryId) {
        return repository
                .findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // PRIVATE METHODS

    private Producto findProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    private void validateCategory(Long categoryId) {
        categoryClient.getCategory(categoryId);
    }

    private Producto buildProduct(ProductFullRequestDTO request) {

        Producto product = new Producto();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategoryId(request.getCategoryId());
        product.setActive(true);
        return product;
    }

    private InventoryRequestDTO
    buildInventoryRequest(Long productId, Integer stock) {

        InventoryRequestDTO dto = new InventoryRequestDTO();
        dto.setProductId(productId);
        dto.setStock(stock);
        return dto;
    }

    private void updateFields(Producto product, ProductRequestDTO request
    ) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategoryId(request.getCategoryId());
    }

    private ProductResponseDTO
    mapToResponse(Producto product) {

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategoryId());

        return dto;
    }
}