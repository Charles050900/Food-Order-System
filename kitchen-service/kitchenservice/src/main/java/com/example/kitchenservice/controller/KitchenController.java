package com.example.kitchenservice.controller;

import com.example.kitchenservice.entity.KitchenOrder;
import com.example.kitchenservice.service.KitchenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
@CrossOrigin("*")
public class KitchenController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    @PostMapping
    public KitchenOrder createOrder(
            @RequestBody KitchenOrder order) {

        return kitchenService.createKitchenOrder(order);
    }

    @GetMapping
    public List<KitchenOrder> getAllOrders() {

        return kitchenService.getAllOrders();
    }

    @GetMapping("/{id}")
    public KitchenOrder getOrder(
            @PathVariable Long id) {

        return kitchenService.getOrder(id);
    }

    @PutMapping("/{id}/ready")
    public KitchenOrder markReady(
            @PathVariable Long id) {

        return kitchenService.markReady(id);
    }
}