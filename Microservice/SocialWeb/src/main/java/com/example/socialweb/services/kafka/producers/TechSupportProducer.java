package com.example.socialweb.services.kafka.producers;

import com.example.socialweb.models.requestModels.TechSupportRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TechSupportProducer {
    private final KafkaTemplate<String, TechSupportRequest> kafkaTemplate;
    private final String topic;

    public TechSupportProducer(KafkaTemplate<String, TechSupportRequest> kafkaTemplate, @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(TechSupportRequest request){
        kafkaTemplate.send(topic, request);
        log.info(String.format("Message sent -> %s", request.getMessage()));
    }
}
