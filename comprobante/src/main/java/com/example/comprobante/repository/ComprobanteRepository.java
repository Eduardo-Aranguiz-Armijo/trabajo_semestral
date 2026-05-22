package com.example.comprobante.repository;

import com.example.comprobante.model.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {

    Optional<Comprobante> findByPaymentId(Long paymentId);

    List<Comprobante> findByOrderIdOrderByCreatedAtDesc(Long orderId);

    List<Comprobante> findByClienteIdOrderByCreatedAtDesc(Long clienteId);

    boolean existsByPaymentId(Long paymentId);
}
