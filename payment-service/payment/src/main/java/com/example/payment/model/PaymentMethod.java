package com.example.payment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    @Column(nullable = false)
    private String cardHolder;

    @Column(nullable = false,
            unique = true,
            length = 16)
    private String cardNumber;

    @Column(nullable = false,
            length = 5)
    private String expirationDate;

    @Column(nullable = false,
            length = 4)
    private String cvv;

    private LocalDateTime createdAt;
}
