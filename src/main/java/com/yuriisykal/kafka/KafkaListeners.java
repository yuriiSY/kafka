package com.yuriisykal.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @KafkaListener(topics = "yuriisykal",groupId = "groupId")
    void listener(ConsumerRecord<String, Message> record) {
        System.out.println("Received:" + record.value());
    }
}
