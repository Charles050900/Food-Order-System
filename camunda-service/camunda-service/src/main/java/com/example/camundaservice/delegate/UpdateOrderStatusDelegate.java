package com.example.camundaservice.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("updateOrderStatusDelegate")
public class UpdateOrderStatusDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Long orderId = (Long) execution.getVariable("orderId");
        
        // Check if payment was successful or failed to determine status
        Boolean paymentSuccess = (Boolean) execution.getVariable("paymentSuccess");
        String status = (paymentSuccess != null && paymentSuccess) ? "DELIVERED" : "CANCELLED";
        
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/api/orders/" + orderId + "/status?status=" + status;
        
        try {
            restTemplate.put(url, null);
        } catch (Exception e) {
            System.err.println("Error calling Order Service to update status: " + e.getMessage());
        }
    }
}
