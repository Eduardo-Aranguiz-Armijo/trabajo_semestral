package com.example.catalogos.service;

import com.example.catalogos.client.ProductClient;
import com.example.catalogos.dto.CategoryRequestDTO;
import com.example.catalogos.dto.CategoryResponseDTO;
import com.example.catalogos.dto.ProductResponseDTO;
import com.example.catalogos.model.Catalogo;
import com.example.catalogos.repository.CatalogoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {

    private final CatalogoRepository repository;
    private final ProductClient productClient;

    public CatalogoService(CatalogoRepository repository,
                           ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    // 🔥 CREATE
    public CategoryResponseDTO create(CategoryRequestDTO request) {

        Catalogo category = new Catalogo();
        category.setNombre(request.getName());
        category.setDescripcion(request.getDescription());

        Catalogo saved = repository.save(category);

        return mapToResponse(saved);
    }

    // 🔥 GET BY ID
    public CategoryResponseDTO getById(Long id) {

        Catalogo category = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada"));

        return mapToResponse(category);
    }

    // 🔥 GET ALL
    public List<CategoryResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 UPDATE
    public CategoryResponseDTO update(Long id,
                                      CategoryRequestDTO request) {

        Catalogo category = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada"));

        category.setNombre(request.getName());
        category.setDescripcion(request.getDescription());

        Catalogo updated = repository.save(category);

        return mapToResponse(updated);
    }

    // 🔥 DELETE
    public void delete(Long id) {

        Catalogo category = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada"));

        repository.delete(category);
    }

    // 🔥 OBTENER PRODUCTOS POR CATEGORÍA
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {

        repository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Categoría no encontrada"));

        return productClient.getProductsByCategory(categoryId);
    }


    // 🔥 MAPEO DTO
    private CategoryResponseDTO mapToResponse(Catalogo category) {

        CategoryResponseDTO dto = new CategoryResponseDTO();

        dto.setId(category.getId());
        dto.setName(category.getNombre());
        dto.setDescription(category.getDescripcion());

        return dto;
    }
}