package com.example.payment.service;

import com.example.payment.client.ComprobanteClient;
import com.example.payment.client.NotificationClient;
import com.example.payment.client.OrderClient;
import com.example.payment.dto.OrderResponseDTO;
import com.example.payment.dto.PaymentRequestDTO;
import com.example.payment.dto.PaymentResponseDTO;
import com.example.payment.exception.PaymentNotFoundException;
import com.example.payment.model.Payment;
import com.example.payment.model.PaymentMethod;
import com.example.payment.repository.PaymentMethodRepository;
import com.example.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class PaymentService {
    private final PaymentRepository repository;

    private final PaymentMethodRepository
            paymentMethodRepository;

    private final OrderClient orderClient;
    private final NotificationClient notificationClient;
    private final ComprobanteClient comprobanteClient;

    public PaymentService(
            PaymentRepository repository,
            PaymentMethodRepository paymentMethodRepository,
            OrderClient orderClient,
            NotificationClient notificationClient,
            ComprobanteClient comprobanteClient
    ) {
        this.repository = repository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderClient = orderClient;
        this.notificationClient = notificationClient;
        this.comprobanteClient = comprobanteClient;
    }

    public PaymentResponseDTO create(
            PaymentRequestDTO request
    ) {

        OrderResponseDTO order =
                orderClient.getOrder(
                        request.getOrderId()
                );

        PaymentMethod method =
                paymentMethodRepository
                        .findById(
                                request
                                        .getPaymentMethodId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment method not found"
                                )
                        );

        if (!method.getClienteId()
                .equals(order.getClienteId())) {

            throw new RuntimeException(
                    "Payment method does not belong to customer"
            );
        }

        Payment payment =
                new Payment();

        payment.setOrderId(order.getId());

        payment.setClienteId(
                order.getClienteId()
        );

        payment.setPaymentMethodId(
                method.getId()
        );

        payment.setAmount(
                order.getTotal()
        );

        payment.setStatus("PAID");

        payment.setPaidAt(
                LocalDateTime.now()
        );

        Payment saved =
                repository.save(payment);

        orderClient.updateStatus(
                order.getId(),
                "PAID"
        );

        notificationClient.sendPaymentSuccessNotification(
                saved.getClienteId(),
                saved.getOrderId(),
                saved.getId(),
                saved.getAmount()
        );

        comprobanteClient.generateComprobante(
                saved.getId(),
                saved.getOrderId(),
                saved.getClienteId(),
                saved.getAmount()
        );

        return map(saved);
    }

    public PaymentResponseDTO getById(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
        return map(payment);
    }

    private PaymentResponseDTO map(
            Payment payment
    ) {

        PaymentResponseDTO dto =
                new PaymentResponseDTO();

        dto.setId(payment.getId());

        dto.setOrderId(
                payment.getOrderId()
        );

        dto.setClienteId(
                payment.getClienteId()
        );

        dto.setPaymentMethodId(
                payment.getPaymentMethodId()
        );

        dto.setAmount(
                payment.getAmount()
        );

        dto.setStatus(
                payment.getStatus()
        );

        dto.setPaidAt(
                payment.getPaidAt()
        );

        return dto;
    }
}
