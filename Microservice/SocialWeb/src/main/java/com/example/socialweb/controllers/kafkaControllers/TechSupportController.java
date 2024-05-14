package com.example.socialweb.controllers.kafkaControllers;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.exceptions.WrongFormatException;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.services.converters.MessageConverter;
import com.example.socialweb.services.techSupport.TechSupportService;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
@UserControllersExceptionHandler
public class TechSupportController {
    private final TechSupportService techSupportService;
    private final UserService userService;

    // Send message to tech support
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageModel messageModel) throws WrongFormatException {
        techSupportService.sendMessage(MessageConverter.convertMessageToTechSupportRequest(userService.getCurrentUser(), messageModel));
        return ResponseEntity.ok("Message has been sent.");
    }
}
