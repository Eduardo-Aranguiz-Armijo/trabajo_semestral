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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private OrderClient orderClient;

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private ComprobanteClient comprobanteClient;

    @InjectMocks
    private PaymentService service;

    @Test
    void debeCrearPago() {

        PaymentRequestDTO request =
                new PaymentRequestDTO();

        request.setOrderId(1L);
        request.setPaymentMethodId(10L);

        OrderResponseDTO order =
                new OrderResponseDTO();

        order.setId(1L);
        order.setClienteId(100L);
        order.setTotal(5000.0);

        PaymentMethod method =
                new PaymentMethod();

        method.setId(10L);
        method.setClienteId(100L);

        Payment saved =
                new Payment();

        saved.setId(50L);
        saved.setOrderId(1L);
        saved.setClienteId(100L);
        saved.setPaymentMethodId(10L);
        saved.setAmount(5000.0);
        saved.setStatus("PAID");
        saved.setPaidAt(LocalDateTime.now());

        when(orderClient.getOrder(1L))
                .thenReturn(order);

        when(paymentMethodRepository.findById(10L))
                .thenReturn(Optional.of(method));

        when(repository.save(any(Payment.class)))
                .thenReturn(saved);

        PaymentResponseDTO result =
                service.create(request);

        assertNotNull(result);
        assertEquals(50L, result.getId());
        assertEquals(5000.0, result.getAmount());
        assertEquals("PAID", result.getStatus());

        verify(orderClient)
                .updateStatus(1L, "PAID");

        verify(notificationClient)
                .sendPaymentSuccessNotification(
                        100L,
                        1L,
                        50L,
                        5000.0
                );

        verify(comprobanteClient)
                .generateComprobante(
                        50L,
                        1L,
                        100L,
                        5000.0
                );
    }

    @Test
    void debeFallarSiMetodoPagoNoExiste() {

        PaymentRequestDTO request =
                new PaymentRequestDTO();

        request.setOrderId(1L);
        request.setPaymentMethodId(99L);

        OrderResponseDTO order =
                new OrderResponseDTO();

        order.setId(1L);
        order.setClienteId(100L);

        when(orderClient.getOrder(1L))
                .thenReturn(order);

        when(paymentMethodRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> service.create(request)
        );

        verify(repository, never())
                .save(any());
    }

    @Test
    void debeFallarSiMetodoNoPerteneceAlCliente() {

        PaymentRequestDTO request =
                new PaymentRequestDTO();

        request.setOrderId(1L);
        request.setPaymentMethodId(10L);

        OrderResponseDTO order =
                new OrderResponseDTO();

        order.setId(1L);
        order.setClienteId(100L);

        PaymentMethod method =
                new PaymentMethod();

        method.setId(10L);
        method.setClienteId(999L);

        when(orderClient.getOrder(1L))
                .thenReturn(order);

        when(paymentMethodRepository.findById(10L))
                .thenReturn(Optional.of(method));

        assertThrows(
                RuntimeException.class,
                () -> service.create(request)
        );

        verify(repository, never())
                .save(any());
    }

    @Test
    void debeRetornarPagoPorId() {

        Payment payment =
                new Payment();

        payment.setId(1L);
        payment.setOrderId(20L);
        payment.setClienteId(30L);
        payment.setPaymentMethodId(40L);
        payment.setAmount(10000.0);
        payment.setStatus("PAID");
        payment.setPaidAt(LocalDateTime.now());

        when(repository.findById(1L))
                .thenReturn(Optional.of(payment));

        PaymentResponseDTO result =
                service.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals(20L, result.getOrderId());
        assertEquals(10000.0, result.getAmount());

        verify(repository).findById(1L);
    }

    @Test
    void debeLanzarExcepcionCuandoPagoNoExiste() {

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                PaymentNotFoundException.class,
                () -> service.getById(999L)
        );

        verify(repository).findById(999L);
    }
}