package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.configurations.utils.ServerUtils;
import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.userServices.MessageService;
import com.example.socialweb.services.userServices.UserService;
import com.example.socialweb.services.converters.MessageConverter;
import com.example.socialweb.services.converters.UserConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;

    @PostMapping("/{id}")
    public ResponseEntity<String> sendMessage(@PathVariable("id") Long id, @RequestBody MessageModel messageModel, HttpServletRequest request) {
        User from = ServerUtils.getUserFromSession(request);
        User to = userService.getUserById(id);
        try {
            messageService.send(from, to, messageModel);
            log.info("Message has been sent.");
            return ResponseEntity.ok("Message has been sent.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> mySenders(HttpServletRequest request) {
        User recipient = ServerUtils.getUserFromSession(request);
        List<Message> messages = messageService.getAllByRecipient(recipient);
        if (!messages.isEmpty()) {
            List<User> senders = MessageConverter.convertMessageToSender(messages);
            List<ProfileModel> profileModels = UserConverter.convertUserToProfileModel(senders);
            return ResponseEntity.ok(profileModels);
        } else
            return ResponseEntity.ok("You have not messages.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> messagesBySender(@PathVariable("id") Long id, HttpServletRequest request) {
        User sender = userService.getUserById(id);
        User recipient = ServerUtils.getUserFromSession(request);
        try {
            List<MessageModel> messages = messageService.getMessagesFromUser(sender, recipient);
            return ResponseEntity.ok(messages);
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}