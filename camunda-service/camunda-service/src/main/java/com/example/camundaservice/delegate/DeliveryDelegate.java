package com.example.camundaservice.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component("deliveryDelegate")
public class DeliveryDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Long orderId = (Long) execution.getVariable("orderId");
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8084/api/delivery";
        
        Map<String, Object> request = new HashMap<>();
        request.put("orderId", orderId);
        
        try {
            restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            System.err.println("Error calling Delivery Service: " + e.getMessage());
        }
    }
}
