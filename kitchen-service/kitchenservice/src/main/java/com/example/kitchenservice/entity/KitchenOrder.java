package com.example.kitchenservice.entity;

import com.example.kitchenservice.enums.KitchenStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "kitchen_orders")
@Data
public class KitchenOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String itemName;

    @Enumerated(EnumType.STRING)
    private KitchenStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}