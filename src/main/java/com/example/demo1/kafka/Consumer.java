package com.example.demo1.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "ticket-topic", groupId = "ticket-group")
    public void consume(String message) {
        System.out.println("From Kafka: " +message);
    }
}
