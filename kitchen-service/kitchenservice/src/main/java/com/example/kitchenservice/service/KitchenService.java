package com.example.kitchenservice.service;

import com.example.kitchenservice.entity.KitchenOrder;
import com.example.kitchenservice.enums.KitchenStatus;
import com.example.kitchenservice.repository.KitchenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KitchenService {

    private final KitchenRepository repository;

    public KitchenService(KitchenRepository repository) {
        this.repository = repository;
    }

    public KitchenOrder createKitchenOrder(KitchenOrder order) {

        order.setStatus(KitchenStatus.READY);
        KitchenOrder savedOrder = repository.save(order);
        
        System.out.println("[KitchenService] Order #" + order.getOrderId() + " - Kitchen ticket created, preparing food... READY");

        return savedOrder;
    }

    public List<KitchenOrder> getAllOrders() {
        return repository.findAll();
    }

    public KitchenOrder getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Kitchen order not found"));
    }

    public KitchenOrder markReady(Long id) {

        KitchenOrder order = getOrder(id);

        order.setStatus(KitchenStatus.READY);

        return repository.save(order);
    }
}