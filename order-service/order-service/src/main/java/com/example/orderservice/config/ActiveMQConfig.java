package com.example.orderservice.config;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMQConfig {

    @Bean
    public Queue orderQueue() {
        return new ActiveMQQueue("order.created");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public org.apache.activemq.broker.BrokerService broker() throws Exception {
        org.apache.activemq.broker.BrokerService broker = new org.apache.activemq.broker.BrokerService();
        broker.addConnector("tcp://localhost:61616");
        broker.setPersistent(false);
        broker.setUseJmx(false);
        return broker;
    }
}