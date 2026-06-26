package com.example.kitchenservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class KitchenserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KitchenserviceApplication.class, args);
    }

}
