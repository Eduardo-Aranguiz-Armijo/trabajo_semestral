package com.example.carrito.repository;

import com.example.carrito.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByClientIdAndStatus(Long clientId, String status);

    List<Cart> findByClientId(Long clientId);



}