package com.example.socialweb.controllers.kafkaControllers;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.exceptions.WrongFormatException;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.services.converters.MessageConverter;
import com.example.socialweb.services.techSupport.TechSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class TechSupportController {
    private final TechSupportService techSupportService;
    private final Cache cache;

    // Send message to tech support
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageModel messageModel) {
        try {
            techSupportService.sendMessage(MessageConverter.convertMessageToTechSupportRequest(cache.getUser(), messageModel));
        } catch (WrongFormatException e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("Message has been sent.");
    }
}
