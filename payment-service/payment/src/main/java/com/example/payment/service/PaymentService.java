package com.example.payment.service;

import com.example.payment.client.OrderClient;
import com.example.payment.dto.OrderResponseDTO;
import com.example.payment.dto.PaymentRequestDTO;
import com.example.payment.dto.PaymentResponseDTO;
import com.example.payment.exception.PaymentAlreadyProcessedException;
import com.example.payment.exception.PaymentMethodNotFoundException;
import com.example.payment.exception.UnauthorizedPaymentMethodException;
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

    public PaymentService(
            PaymentRepository repository, PaymentMethodRepository paymentMethodRepository, OrderClient orderClient
    ) {

        this.repository = repository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderClient = orderClient;
    }

    // =========================================
    // CREATE PAYMENT
    // =========================================

    public PaymentResponseDTO create(
            PaymentRequestDTO request
    ) {
        OrderResponseDTO order = getOrder(request.getOrderId());
        validateOrderNotPaid(order);
        PaymentMethod method = getPaymentMethod(request.getPaymentMethodId());
        validatePaymentOwnership(method, order);
        Payment payment = buildPayment(order, method);
        Payment saved = repository.save(payment);
        updateOrderStatus(order.getId()
        );
        return map(saved);
    }

    // =========================================
    // PRIVATE METHODS
    // =========================================

    private OrderResponseDTO getOrder(Long orderId) {
        return orderClient.getOrder(orderId);
    }
    private PaymentMethod getPaymentMethod(
            Long paymentMethodId
    ) {
        return paymentMethodRepository
                .findById(paymentMethodId)
                .orElseThrow(() -> new PaymentMethodNotFoundException("Payment method not found"));
    }

    private void validatePaymentOwnership(PaymentMethod method, OrderResponseDTO order) {

        if (!method.getClientId()
                .equals(order.getClientId())) {
            throw new UnauthorizedPaymentMethodException("Payment method does not belong to customer");
        }
    }

    private void validateOrderNotPaid(
            OrderResponseDTO order
    ) {
        if (order.getStatus().equalsIgnoreCase("PAID")) {
            throw new PaymentAlreadyProcessedException("Order already paid");
        }
    }

    private Payment buildPayment(
            OrderResponseDTO order,
            PaymentMethod method
    ) {

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setClientId(order.getClientId());
        payment.setPaymentMethodId(method.getId());
        payment.setAmount(order.getTotal());
        payment.setStatus("PAID");
        payment.setPaidAt(LocalDateTime.now());

        return payment;
    }

    private void updateOrderStatus(Long orderId) {
        orderClient.updateStatus(orderId, "PAID");
    }

    // =========================================
    // MAPPER
    // =========================================

    private PaymentResponseDTO map(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrderId());
        dto.setClientId(payment.getClientId());
        dto.setPaymentMethodId(payment.getPaymentMethodId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaidAt(payment.getPaidAt());

        return dto;
    }
}