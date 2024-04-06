package com.example.techsupport.services.kafka.consumers;

import com.example.techsupport.models.requestModels.TechSupportRequestModel;
import com.example.techsupport.services.TechSupportService;
import com.example.techsupport.services.converters.TechSupportRequestConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TechSupportConsumer {
    private final TechSupportService techSupportService;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupid}", containerFactory = "listenerContainerFactory")
    public void consume(TechSupportRequestModel requestModel) {
        techSupportService.save(TechSupportRequestConverter.convert(requestModel));
        log.info(String.format("Message has been received and saved -> %s", requestModel.getMessage()));
    }
}
