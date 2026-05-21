package com.example.notificaciones.service;

import com.example.notificaciones.client.OrderClient;
import com.example.notificaciones.dto.NotificationRequestDTO;
import com.example.notificaciones.dto.NotificationResponseDTO;
import com.example.notificaciones.dto.OrderResponseDTO;
import com.example.notificaciones.exception.NotificationNotFoundException;
import com.example.notificaciones.exception.NotificationSendException;
import com.example.notificaciones.model.Notification;
import com.example.notificaciones.model.NotificationStatus;
import com.example.notificaciones.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final OrderClient orderClient;

    public NotificationService(
            NotificationRepository repository,
            OrderClient orderClient
    ) {
        this.repository = repository;
        this.orderClient = orderClient;
    }

    public NotificationResponseDTO createAndSend(NotificationRequestDTO request) {
        validateOrderIfPresent(request);

        Notification notification = new Notification();
        notification.setClienteId(request.getClienteId());
        notification.setOrderId(request.getOrderId());
        notification.setPaymentId(request.getPaymentId());
        notification.setType(request.getType());
        notification.setChannel(request.getChannel());
        notification.setSubject(request.getSubject());
        notification.setMessage(resolveMessage(request));
        notification.setStatus(NotificationStatus.PENDING);
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = repository.save(notification);
        sendNotification(saved);

        return map(saved);
    }

    public NotificationResponseDTO getById(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
        return map(notification);
    }

    public List<NotificationResponseDTO> getByClienteId(Long clienteId) {
        return repository.findByClienteIdOrderByCreatedAtDesc(clienteId)
                .stream()
                .map(this::map)
                .toList();
    }

    public List<NotificationResponseDTO> getByOrderId(Long orderId) {
        return repository.findByOrderIdOrderByCreatedAtDesc(orderId)
                .stream()
                .map(this::map)
                .toList();
    }

    public NotificationResponseDTO resend(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));

        if (notification.getStatus() == NotificationStatus.SENT) {
            throw new NotificationSendException("La notificación ya fue enviada");
        }

        sendNotification(notification);
        return map(notification);
    }

    private void validateOrderIfPresent(NotificationRequestDTO request) {
        if (request.getOrderId() == null) {
            return;
        }

        try {
            OrderResponseDTO order = orderClient.getOrder(request.getOrderId());
            if (order.getClienteId() != null
                    && !order.getClienteId().equals(request.getClienteId())) {
                throw new NotificationSendException(
                        "El cliente no coincide con la orden indicada"
                );
            }
        } catch (Exception ex) {
            if (ex instanceof NotificationSendException sendException) {
                throw sendException;
            }
        }
    }

    private void sendNotification(Notification notification) {
        try {
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            repository.save(notification);
        } catch (Exception ex) {
            notification.setStatus(NotificationStatus.FAILED);
            repository.save(notification);
            throw new NotificationSendException("No se pudo enviar la notificación");
        }
    }

    private String resolveMessage(NotificationRequestDTO request) {
        if (request.getMessage() != null && !request.getMessage().isBlank()) {
            return request.getMessage();
        }

        return switch (request.getType()) {
            case PAYMENT_SUCCESS -> "Su pago fue procesado correctamente.";
            case PAYMENT_FAILED -> "No se pudo procesar su pago. Intente nuevamente.";
            case ORDER_CREATED -> "Su orden fue registrada correctamente.";
            case ORDER_STATUS_CHANGED -> "El estado de su orden fue actualizado.";
        };
    }

    private NotificationResponseDTO map(Notification notification) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setClienteId(notification.getClienteId());
        dto.setOrderId(notification.getOrderId());
        dto.setPaymentId(notification.getPaymentId());
        dto.setType(notification.getType());
        dto.setChannel(notification.getChannel());
        dto.setSubject(notification.getSubject());
        dto.setMessage(notification.getMessage());
        dto.setStatus(notification.getStatus());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setSentAt(notification.getSentAt());
        return dto;
    }
}
