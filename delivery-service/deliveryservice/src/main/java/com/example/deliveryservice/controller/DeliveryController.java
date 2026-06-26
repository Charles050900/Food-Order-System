package com.example.deliveryservice.controller;

import com.example.deliveryservice.entity.Delivery;
import com.example.deliveryservice.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@CrossOrigin("*")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public Delivery createDelivery(
            @RequestBody Delivery delivery) {

        return deliveryService.createDelivery(delivery);
    }

    @GetMapping
    public List<Delivery> getAllDeliveries() {

        return deliveryService.getAllDeliveries();
    }

    @PutMapping("/{id}/start")
    public Delivery startDelivery(
            @PathVariable Long id) {

        return deliveryService.startDelivery(id);
    }

    @PutMapping("/{id}/delivered")
    public Delivery markDelivered(
            @PathVariable Long id) {

        return deliveryService.markDelivered(id);
    }
}