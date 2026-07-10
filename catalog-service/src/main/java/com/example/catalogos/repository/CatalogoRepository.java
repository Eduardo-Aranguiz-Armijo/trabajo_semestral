package com.example.catalogos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.catalogos.model.Catalogo;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {

    Catalogo findByName(String name);

}
