package com.example.productos.service;

import com.example.productos.client.CategoryClient;
import com.example.productos.client.InventoryClient;
import com.example.productos.dto.ProductFullRequestDTO;
import com.example.productos.dto.ProductResponseDTO;
import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private ProductService service;

    @Test
    void deberiaRetornarProductoPorId() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Laptop");
        producto.setPrice(23.0);
        producto.setCategoryId(1L);
        producto.setActive(true);
        producto.setDescription("laptop hp windows 10");

        when(repository.findById(1L))
                .thenReturn(Optional.of(producto));

        ProductResponseDTO resultado = service.getById(1L);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getName());

        verify(repository).findById(1L);
    }

        @Test
        void deberiaCrearProductoCompletoCorrectamente() {

            // Arrange
            ProductFullRequestDTO request = new ProductFullRequestDTO();
            request.setName("Laptop");
            request.setDescription("Laptop HP");
            request.setPrice(2500.0);
            request.setCategoryId(1L);
            request.setStock(10);

            Producto productoGuardado = new Producto();
            productoGuardado.setId(1L);
            productoGuardado.setName("Laptop");
            productoGuardado.setDescription("Laptop HP");
            productoGuardado.setPrice(2500.0);
            productoGuardado.setCategoryId(1L);
            productoGuardado.setActive(true);

            when(repository.save(any(Producto.class)))
                    .thenReturn(productoGuardado);

            // Act
            ProductResponseDTO resultado =
                    service.createFullProduct(request);

            // Assert
            assertNotNull(resultado);
            assertEquals(1L, resultado.getId());
            assertEquals("Laptop", resultado.getName());

            verify(repository).save(any(Producto.class));

            verify(inventoryClient).createInventory(
                    argThat(inventory ->
                            inventory.getProductId().equals(1L)
                                    && inventory.getStock() == 10
                    )
            );
        }

    @Test
    void deberiaRetornarProductosCorrectamente() {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setName("Laptop");

        Producto producto2 = new Producto();
        producto2.setId(1L);
        producto2.setName("Mouse");

        Producto producto3 = new Producto();
        producto3.setId(1L);
        producto3.setName("Teclado");


        when(repository.findAll())
                .thenReturn(List.of(producto1,producto2,producto3));
        List<ProductResponseDTO> resultado  = service.getAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(3,resultado.size());

        assertEquals("Laptop", resultado.get(0).getName());
        assertEquals("Mouse", resultado.get(1).getName());
        assertEquals("Teclado", resultado.get(2).getName());
        verify(repository).findAll();
    }
    @Test
    void deberiaRetornarProductosPorCategoryIdCorrectamente(){
        Producto producto1 = new Producto();
        producto1.setCategoryId(1L);
        producto1.setName("Laptop");

        Producto producto2 = new Producto();
        producto2.setCategoryId(1L);
        producto2.setName("Mouse");

        Producto producto3 = new Producto();
        producto3.setCategoryId(1L);
        producto3.setName("Teclado");



        when(repository.findByCategoryId(1L)).thenReturn(List.of(producto1,producto2,producto3));
        List<ProductResponseDTO> resultado  = service.getByCategory(1L);
        assertNotNull(resultado);
        assertEquals(3,resultado.size());

        assertEquals("Laptop", resultado.get(0).getName());
        assertEquals("Mouse", resultado.get(1).getName());
        assertEquals("Teclado", resultado.get(2).getName());
        verify(repository).findByCategoryId(1L);
    }

    @Test
    void deberiaEliminarProductoCuandoExiste() {

        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Laptop");

        when(repository.findById(1L))
                .thenReturn(Optional.of(producto));

        service.delete(1L);

        verify(repository).findById(1L);
        verify(repository).delete(producto);
    }
    }
