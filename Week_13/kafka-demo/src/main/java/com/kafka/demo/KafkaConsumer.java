package com.kafka.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = {"kafka.test"})
    public void onMessage(ConsumerRecord<?, ?> record) {
        System.out.printf("主题：%s，分区：%s，内容：%s\n", record.topic(), record.partition(), record.value());
    }
}
