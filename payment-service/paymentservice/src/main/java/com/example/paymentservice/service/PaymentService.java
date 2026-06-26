package com.example.paymentservice.service;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Payment payment) {

        // Mock success/fail calculation (80% success rate)
        boolean isSuccess = Math.random() < 0.8;
        payment.setPaymentStatus(isSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

        Payment savedPayment = paymentRepository.save(payment);

        System.out.println("[PaymentService] Order #" + payment.getOrderId() + " - Payment " + savedPayment.getPaymentStatus());

        return savedPayment;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found"));
    }
}