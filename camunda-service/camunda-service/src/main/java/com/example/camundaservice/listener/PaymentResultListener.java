package com.example.camundaservice.listener;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultListener {

        private final RuntimeService runtimeService;

        public PaymentResultListener(
                        RuntimeService runtimeService) {

                this.runtimeService = runtimeService;
        }

        // @JmsListener(destination = "PAYMENT_RESULT_QUEUE")
        // Disabled JMS listener because Payment is now a synchronous REST Service Task
        public void receive(Long orderId) {

                System.out.println(
                                "Payment result received : "
                                                + orderId);

                runtimeService
                                .createMessageCorrelation(
                                                "PaymentCompleted")
                                .processInstanceVariableEquals(
                                                "orderId",
                                                orderId)
                                .setVariable(
                                                "paymentSuccess",
                                                true)
                                .correlate();
        }
}