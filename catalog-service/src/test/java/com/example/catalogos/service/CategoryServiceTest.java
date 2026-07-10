package com.example.catalogos.service;

import com.example.catalogos.client.ProductClient;
import com.example.catalogos.dto.CategoryRequestDTO;
import com.example.catalogos.dto.CategoryResponseDTO;
import com.example.catalogos.dto.ProductResponseDTO;
import com.example.catalogos.model.Catalogo;
import com.example.catalogos.repository.CatalogoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CatalogoRepository repository;
    @Mock
    private ProductClient productClient;
    @InjectMocks
    private CatalogoService service;
    @Test
    void debeCrearCategoria() {

        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("Electrónica");
        request.setDescription("Dispositivos");

        Catalogo saved = new Catalogo();
        saved.setId(1L);
        saved.setNombre("Electrónica");
        saved.setDescripcion("Dispositivos");

        when(repository.save(any(Catalogo.class)))
                .thenReturn(saved);

        CategoryResponseDTO result = service.create(request);

        assertEquals(1L, result.getId());
        assertEquals("Electrónica", result.getName());

        verify(repository).save(any(Catalogo.class));
    }
    @Test
    void debeObtenerCategoriaPorId() {

        Catalogo category = new Catalogo();
        category.setId(1L);
        category.setNombre("Electrónica");

        when(repository.findById(1L))
                .thenReturn(Optional.of(category));

        CategoryResponseDTO result = service.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Electrónica", result.getName());

        verify(repository).findById(1L);
    }
    @Test
    void debeListarCategorias() {

        Catalogo c1 = new Catalogo();
        c1.setId(1L);
        c1.setNombre("A");

        Catalogo c2 = new Catalogo();
        c2.setId(2L);
        c2.setNombre("B");

        when(repository.findAll())
                .thenReturn(List.of(c1, c2));

        List<CategoryResponseDTO> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getName());
        assertEquals("B", result.get(1).getName());

        verify(repository).findAll();
    }
    @Test
    void debeActualizarCategoria() {

        Catalogo existing = new Catalogo();
        existing.setId(1L);
        existing.setNombre("Old");

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(any(Catalogo.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        CategoryRequestDTO request = new CategoryRequestDTO();
        request.setName("New");
        request.setDescription("Updated");

        CategoryResponseDTO result = service.update(1L, request);

        assertEquals("New", result.getName());
        assertEquals("Updated", result.getDescription());

        verify(repository).findById(1L);
        verify(repository).save(any(Catalogo.class));
    }
    @Test
    void debeEliminarCategoria() {

        Catalogo category = new Catalogo();
        category.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(category));

        service.delete(1L);

        verify(repository).findById(1L);
        verify(repository).delete(category);
    }

    @Test
    void debeObtenerProductosPorCategoria() {

        Long categoryId = 1L;

        Catalogo category = new Catalogo();
        category.setId(categoryId);
        category.setNombre("Electrónica");

        when(repository.findById(categoryId))
                .thenReturn(Optional.of(category));

        ProductResponseDTO p1 = new ProductResponseDTO();
        p1.setId(1L);
        p1.setName("TV");

        ProductResponseDTO p2 = new ProductResponseDTO();
        p2.setId(2L);
        p2.setName("Laptop");

        when(productClient.getProductsByCategory(categoryId))
                .thenReturn(List.of(p1, p2));

        List<ProductResponseDTO> result = service.getProductsByCategory(categoryId);

        assertEquals(2, result.size());
        assertEquals("TV", result.get(0).getName());
        assertEquals("Laptop", result.get(1).getName());

        verify(repository).findById(categoryId);
        verify(productClient).getProductsByCategory(categoryId);
    }
}