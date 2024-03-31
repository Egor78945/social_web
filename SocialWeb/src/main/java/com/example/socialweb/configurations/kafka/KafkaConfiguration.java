package com.example.socialweb.configurations.kafka;

import com.example.socialweb.models.requestModels.TechSupportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    private final String topic;
    private final String bootstrap;

    public KafkaConfiguration(@Value("${kafka.topic}") String topic, @Value("${kafka.bootstrap}") String bootstrap) {
        this.topic = topic;
        this.bootstrap = bootstrap;
    }
    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, TechSupportRequest> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, TechSupportRequest>(properties);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));

        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, TechSupportRequest> kafkaTemplate(ProducerFactory<String, TechSupportRequest> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic techSupportTopic() {
        return TopicBuilder
                .name(topic)
                .build();
    }
}
