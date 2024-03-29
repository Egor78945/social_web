package com.example.socialweb.services.kafka.producers;

import com.example.socialweb.models.requestModels.TechSupportRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechSupportProducer {
    private final KafkaTemplate<String, TechSupportRequest> kafkaTemplate;

    public void send(TechSupportRequest request){
        Message<TechSupportRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, "techsupport")
                .build();
        kafkaTemplate.send(message);
        log.info(String.format("Message sent -> %s", request.getMessage()));
    }
}
