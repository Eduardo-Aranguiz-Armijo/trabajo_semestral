package com.example.productos.service;

import com.example.productos.client.CategoryClient;
import com.example.productos.client.InventoryClient;
import com.example.productos.dto.InventoryRequestDTO;
import com.example.productos.dto.ProductFullRequestDTO;
import com.example.productos.dto.ProductResponseDTO;
import com.example.productos.exception.exceptions.InventoryException;
import com.example.productos.exception.exceptions.ProductNotFoundException;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
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
class ProductServiceTest {

    @Mock
    private ProductoRepository repository;

    @Mock
    private CategoryClient categoryClient;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private ProductService productService;

    private Producto dummyProduct;
    private ProductFullRequestDTO fullRequest;

    @BeforeEach
    void setUp() {
        dummyProduct = new Producto();
        dummyProduct.setId(1L);
        dummyProduct.setName("Test Product");
        dummyProduct.setDescription("Desc");
        dummyProduct.setPrice(100.0);
        dummyProduct.setCategoryId(2L);
        dummyProduct.setActive(true);

        fullRequest = new ProductFullRequestDTO();
        fullRequest.setName("Test Product");
        fullRequest.setDescription("Desc");
        fullRequest.setPrice(100.0);
        fullRequest.setCategoryId(2L);
        fullRequest.setStock(50);
    }

    @Test
    void testCreateFullProduct_Success() {
        // Arrange
        when(categoryClient.getCategory(fullRequest.getCategoryId())).thenReturn(null); // Assuming it just doesn't throw exception
        when(repository.save(any(Producto.class))).thenReturn(dummyProduct);
        doNothing().when(inventoryClient).createInventory(any(InventoryRequestDTO.class));

        // Act
        ProductResponseDTO response = productService.createFullProduct(fullRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Test Product", response.getName());
        verify(repository, times(1)).save(any(Producto.class));
        verify(inventoryClient, times(1)).createInventory(any(InventoryRequestDTO.class));
    }

    @Test
    void testCreateFullProduct_InventoryFailRollback() {
        // Arrange
        when(categoryClient.getCategory(fullRequest.getCategoryId())).thenReturn(null);
        when(repository.save(any(Producto.class))).thenReturn(dummyProduct);
        doThrow(new RuntimeException("Connection failed")).when(inventoryClient).createInventory(any(InventoryRequestDTO.class));

        // Act & Assert
        InventoryException exception = assertThrows(InventoryException.class, () -> {
            productService.createFullProduct(fullRequest);
        });

        assertEquals("Connection failed", exception.getMessage());
        // Verify that it tried to delete the product (Rollback)
        verify(repository, times(1)).delete(dummyProduct);
    }

    @Test
    void testGetById_Success() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(dummyProduct));

        // Act
        ProductResponseDTO response = productService.getById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Product", response.getName());
    }

    @Test
    void testGetById_NotFound() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getById(99L);
        });
    }

    @Test
    void testGetAll_Success() {
        // Arrange
        when(repository.findAll()).thenReturn(List.of(dummyProduct));

        // Act
        List<ProductResponseDTO> responseList = productService.getAll();

        // Assert
        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals("Test Product", responseList.get(0).getName());
    }
}
