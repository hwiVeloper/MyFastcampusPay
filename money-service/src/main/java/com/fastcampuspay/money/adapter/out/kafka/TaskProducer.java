package com.fastcampuspay.money.adapter.out.kafka;

import com.fastcampuspay.common.RechargingMoneyTask;
import com.fastcampuspay.money.application.port.out.SendRechargingMoneyTaskPort;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {

    private final KafkaProducer<String, String> producer;

    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}") String topic) {
        Properties props = new Properties();
        props.put("bootstrap.server", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask task) {

    }
}
