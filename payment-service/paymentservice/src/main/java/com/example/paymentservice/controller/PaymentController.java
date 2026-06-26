package com.example.paymentservice.controller;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment processPayment(
            @RequestBody Payment payment) {

        return paymentService.processPayment(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {

        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(
            @PathVariable Long id) {

        return paymentService.getPaymentById(id);
    }
}