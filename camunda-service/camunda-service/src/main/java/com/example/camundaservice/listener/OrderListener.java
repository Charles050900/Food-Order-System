package com.example.camundaservice.listener;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderListener {

    private final RuntimeService runtimeService;

    public OrderListener(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @JmsListener(destination = "order.created")
    public void receiveOrder(Long orderId) {

        System.out.println("Received Order: " + orderId);

        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);

        try {
            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            String orderUrl = "http://localhost:8081/api/orders/" + orderId;
            Map order = restTemplate.getForObject(orderUrl, Map.class);
            if (order != null) {
                variables.put("amount", order.get("amount"));
                variables.put("itemName", order.get("itemName"));
            }
        } catch (Exception e) {
            System.err.println("Error fetching order in listener: " + e.getMessage());
        }

        runtimeService.startProcessInstanceByKey(
                "order-process",
                variables
        );

        System.out.println(
                "Workflow Started for Order " + orderId);
    }
}