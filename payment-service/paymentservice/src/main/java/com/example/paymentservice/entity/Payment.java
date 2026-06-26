package com.example.paymentservice.entity;

import com.example.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentTime;

    @PrePersist
    public void prePersist() {
        this.paymentTime = LocalDateTime.now();
    }
}