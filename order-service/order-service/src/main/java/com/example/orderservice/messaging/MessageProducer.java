package com.example.orderservice.messaging;

import com.example.orderservice.entity.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    private final JmsTemplate jmsTemplate;

    public MessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String event, Order order) {
        jmsTemplate.convertAndSend(event, order);
    }
}