package com.example.inventory.service;

import com.example.inventory.client.ProductClient;
import com.example.inventory.dto.InventoryResponseDTO;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    @Mock
    private InventoryRepository repository;
    @Mock
    private ProductClient productClient;
    @InjectMocks
    private InventoryService service;



    @Test
    void debeRetornarProductoPorId(){
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductId(5L);
        inventory.setStock(20);
        inventory.setUpdatedAt(LocalDateTime.now());

        when(repository.findByProductId(5L)).thenReturn(Optional.of(inventory));

        InventoryResponseDTO response = service.getByProduct(5L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(5L, response.getProductId());
        assertEquals(20, response.getStock());

        verify(repository).findByProductId(5L);


    }

    @Test
    void deberiaActualizarStock() {

        // Arrange
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductId(5L);
        inventory.setStock(10);

        when(repository.findByProductId(5L))
                .thenReturn(Optional.of(inventory));

        when(repository.save(any(Inventory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        InventoryResponseDTO response =
                service.updateStock(5L, 50);

        // Assert
        assertNotNull(response);

        assertEquals(1L, response.getId());
        assertEquals(5L, response.getProductId());
        assertEquals(50, response.getStock());

        assertNotNull(response.getUpdatedAt());

        verify(repository).findByProductId(5L);
        verify(repository).save(any(Inventory.class));
    }

    @Test
    void deberiaLanzarExcepcionSiNoExisteInventarioAlActualizar() {

        when(repository.findByProductId(5L))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.updateStock(5L, 50));

        verify(repository).findByProductId(5L);

        verify(repository, never())
                .save(any());
    }

    @Test
    void deberiaDisminuirStock() {

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductId(5L);
        inventory.setStock(20);

        when(repository.findByProductId(5L))
                .thenReturn(Optional.of(inventory));

        when(repository.save(any(Inventory.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        InventoryResponseDTO response =
                service.decreaseStock(5L, 5);

        assertNotNull(response);

        assertEquals(15, response.getStock());

        assertNotNull(response.getUpdatedAt());

        verify(repository).findByProductId(5L);
        verify(repository).save(any(Inventory.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoNoHayStockSuficiente() {

        Inventory inventory = new Inventory();
        inventory.setProductId(5L);
        inventory.setStock(3);

        when(repository.findByProductId(5L)).thenReturn(Optional.of(inventory));

        RuntimeException exception =  assertThrows(RuntimeException.class, () -> service.decreaseStock(5L,10));
        assertEquals("Insufficient stock", exception.getMessage());

        verify(repository).findByProductId(5L);

        verify(repository, never()).save(any());

    }
}
