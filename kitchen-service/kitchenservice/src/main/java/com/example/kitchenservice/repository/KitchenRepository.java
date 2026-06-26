package com.example.kitchenservice.repository;

import com.example.kitchenservice.entity.KitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KitchenRepository extends JpaRepository<KitchenOrder, Long> {
}