package com.example.orden.repository;

import com.example.orden.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository  extends JpaRepository<Orden, Long>{
}
