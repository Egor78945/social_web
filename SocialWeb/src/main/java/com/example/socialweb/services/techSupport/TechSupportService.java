package com.example.socialweb.services.techSupport;

import com.example.socialweb.exceptions.WrongFormatException;
import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.requestModels.TechSupportRequest;
import com.example.socialweb.services.kafka.producers.TechSupportProducer;
import com.example.socialweb.services.validation.MessageValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechSupportService {
    private final TechSupportProducer techSupportProducer;
    public void sendMessage(TechSupportRequest techSupportRequest) throws WrongFormatException {
        if(MessageValidation.checkMessageValid(techSupportRequest.getMessage())) {
            techSupportProducer.send(techSupportRequest);
        } else {
            throw new WrongFormatException("This message is too short or too long.");
        }
    }
}
