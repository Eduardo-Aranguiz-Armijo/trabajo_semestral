package com.example.catalogos.service;

import com.example.catalogos.client.ProductClient;
import com.example.catalogos.dto.CategoryRequestDTO;
import com.example.catalogos.dto.CategoryResponseDTO;
import com.example.catalogos.dto.ProductResponseDTO;
import com.example.catalogos.exception.CategoryNotFoundException;
import com.example.catalogos.model.Catalogo;
import com.example.catalogos.repository.CatalogoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {

    private final CatalogoRepository repository;
    private final ProductClient productClient;
    //inyeccion manual
    public CatalogoService(CatalogoRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    // CREATE

    public CategoryResponseDTO create(CategoryRequestDTO request) {

        Catalogo category = buildCategory(request);
        Catalogo saved = repository.save(category);

        return mapToResponse(saved);
    }

    // GET BY ID

    public CategoryResponseDTO getById(Long id) {

        Catalogo category = findCategory(id);

        return mapToResponse(category);
    }

    // GET ALL

    public List<CategoryResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE

    public CategoryResponseDTO update(Long id, CategoryRequestDTO request) {

        Catalogo category = findCategory(id);

        updateFields(category, request);

        Catalogo updated = repository.save(category);

        return mapToResponse(updated);
    }

    // DELETE

    public void delete(Long id) {

        Catalogo category = findCategory(id);

        repository.delete(category);
    }

    // GET PRODUCTS BY CATEGORY

    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {

        findCategory(categoryId);

        return productClient.getProductsByCategory(categoryId);
    }

    // PRIVATE METHODS

    private Catalogo findCategory(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found"));
    }

    private Catalogo buildCategory(CategoryRequestDTO request) {

        Catalogo category = new Catalogo();

        updateFields(category, request);

        return category;
    }

    private void updateFields(Catalogo category, CategoryRequestDTO request) {

        category.setName(request.getName());

        category.setDescription(request.getDescription());
    }

    private CategoryResponseDTO mapToResponse(Catalogo category) {

        CategoryResponseDTO dto = new CategoryResponseDTO();

        dto.setId(category.getId());

        dto.setName(category.getName());

        dto.setDescription(category.getDescription());

        return dto;
    }
}