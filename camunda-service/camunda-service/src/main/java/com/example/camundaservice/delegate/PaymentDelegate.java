package com.example.camundaservice.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("paymentDelegate")
public class PaymentDelegate implements JavaDelegate {

        @Override
        public void execute(DelegateExecution execution) {

                Long orderId = (Long) execution.getVariable("orderId");

                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8082/api/payments";

                Double amount = 0.0;
                if (execution.getVariable("amount") != null) {
                        amount = Double.valueOf(execution.getVariable("amount").toString());
                }

                java.util.Map<String, Object> request = new java.util.HashMap<>();
                request.put("orderId", orderId);
                request.put("amount", amount);

                try {
                        java.util.Map response = restTemplate.postForObject(url, request, java.util.Map.class);
                        boolean isSuccess = response != null && "SUCCESS".equals(response.get("paymentStatus"));
                        execution.setVariable("paymentSuccess", isSuccess);
                } catch (Exception e) {
                        System.err.println("Error calling Payment Service: " + e.getMessage());
                        execution.setVariable("paymentSuccess", false);
                }
        }
}
