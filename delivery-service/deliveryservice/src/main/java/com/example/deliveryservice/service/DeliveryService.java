package com.example.deliveryservice.service;

import com.example.deliveryservice.entity.Delivery;
import com.example.deliveryservice.enums.DeliveryStatus;
import com.example.deliveryservice.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository repository;

    public DeliveryService(DeliveryRepository repository) {
        this.repository = repository;
    }

    public Delivery createDelivery(Delivery delivery) {

        delivery.setDeliveryAgent("Driver_" + (int)(Math.random() * 100));
        delivery.setStatus(DeliveryStatus.DELIVERED);
        Delivery savedDelivery = repository.save(delivery);
        
        System.out.println("[DeliveryService] Order #" + delivery.getOrderId() + " - Driver assigned, delivering... DELIVERED");

        return savedDelivery;
    }

    public List<Delivery> getAllDeliveries() {
        return repository.findAll();
    }

    public Delivery getDelivery(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Delivery not found"));
    }

    public Delivery startDelivery(Long id) {

        Delivery delivery = getDelivery(id);

        delivery.setStatus(DeliveryStatus.OUT_FOR_DELIVERY);

        return repository.save(delivery);
    }

    public Delivery markDelivered(Long id) {

        Delivery delivery = getDelivery(id);

        delivery.setStatus(DeliveryStatus.DELIVERED);

        return repository.save(delivery);
    }
}