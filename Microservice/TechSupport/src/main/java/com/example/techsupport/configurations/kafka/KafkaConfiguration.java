package com.example.techsupport.configurations.kafka;

import com.example.techsupport.models.requestModels.TechSupportRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {
    private final String bootstrap;
    private final String autooffsetreset;

    public KafkaConfiguration(@Value("${kafka.bootstrap}") String bootstrap, @Value("${kafka.auto-offset-reset}") String autooffsetreset) {
        this.bootstrap = bootstrap;
        this.autooffsetreset = autooffsetreset;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, TechSupportRequestModel> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TYPE_MAPPINGS, "com.example.socialweb.models.requestModels.TechSupportRequest:com.example.techsupport.models.requestModels.TechSupportRequestModel");
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autooffsetreset);

        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, TechSupportRequestModel>(properties);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(objectMapper));

        return kafkaConsumerFactory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, TechSupportRequestModel>> listenerContainerFactory(ConsumerFactory<String, TechSupportRequestModel> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, TechSupportRequestModel>();

        factory.setConsumerFactory(consumerFactory);

        return factory;
    }
}
