package com.example.ms_users.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    // CORREGIDO: Escapamos 'role' con comillas invertidas para evitar conflictos en MySQL
    @Column(name = "`role`", nullable = false)
    private String role;
}