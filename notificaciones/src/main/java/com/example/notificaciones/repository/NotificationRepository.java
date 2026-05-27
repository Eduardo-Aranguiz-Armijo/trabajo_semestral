package com.example.notificaciones.repository;

import com.example.notificaciones.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByClienteIdOrderByCreatedAtDesc(Long clienteId);

    List<Notification> findByOrderIdOrderByCreatedAtDesc(Long orderId);
}
