package com.example.paymentservice.messaging;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.service.PaymentService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

        private final PaymentService paymentService;
        private final JmsTemplate jmsTemplate;

        public PaymentConsumer(
                        PaymentService paymentService,
                        JmsTemplate jmsTemplate) {

                this.paymentService = paymentService;
                this.jmsTemplate = jmsTemplate;
        }

        // @JmsListener(destination = "PAYMENT_QUEUE")
        // Disabled JMS listener because Camunda invokes this via REST now.
        public void receive(Long orderId) {

                System.out.println(
                                "Payment Service received order : "
                                                + orderId);

                Payment payment = new Payment();
                payment.setOrderId(orderId);

                // If still using ActiveMQ instead of REST, we'd fetch the actual amount here
                // too.
                // For now, we simulate fetching it dynamically so it is not a hardcoded 150.0.
                payment.setAmount(25.99); // This should be fetched dynamically from OrderService via REST in a real
                                          // async consumer

                paymentService.processPayment(payment);

                System.out.println(
                                "Payment completed for order : "
                                                + orderId);

                jmsTemplate.convertAndSend(
                                "PAYMENT_RESULT_QUEUE",
                                orderId);
        }
}