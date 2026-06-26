package com.example.orderservice.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.enums.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final JmsTemplate jmsTemplate;

    public OrderService(OrderRepository orderRepository,JmsTemplate jmsTemplate) {
        this.orderRepository = orderRepository;

        this.jmsTemplate = jmsTemplate;
    }

    public Order createOrder(Order order) {

        order.setStatus(OrderStatus.PLACED);

        Order savedOrder = orderRepository.save(order);

        jmsTemplate.convertAndSend(
                "order.created",
                savedOrder.getId());

        System.out.println("[OrderService] Order #" + savedOrder.getId() + " - Status: PLACED, Workflow started");

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    public Order updateOrderStatus(Long orderId,
                                   OrderStatus status) {

        Order order = getOrderById(orderId);

        order.setStatus(status);

        Order savedOrder = orderRepository.save(order);

        if (status == OrderStatus.DELIVERED) {
            System.out.println("[OrderService] Order #" + savedOrder.getId() + " - Workflow COMPLETE");
        } else if (status == OrderStatus.CANCELLED) {
            System.out.println("[OrderService] Order #" + savedOrder.getId() + " - CANCELLED");
        }

        return savedOrder;
    }
}