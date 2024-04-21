package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.configurations.utils.Cache;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.userServices.MessageService;
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
    private final MessageService messageService;
    private final Cache cache;

    // Send message to any user by id:
    @PostMapping("/{id}")
    public ResponseEntity<String> sendMessage(@PathVariable("id") Long toId, @RequestBody MessageModel messageModel) {
        User from = cache.getUser();
        try {
            messageService.send(from, toId, messageModel);
            log.info("Message has been sent.");
            return ResponseEntity.ok("Message has been sent.");
        } catch (RequestRejectedException e) {
            log.info(e.getMessage());
            return ResponseEntity.ok(e.getMessage());
        }
    }

    // Show all users, who wrote any messages to me
    @GetMapping
    public ResponseEntity<?> mySenders() {
        List<ProfileModel> messages = messageService.getAllSendersMessage(cache.getUser().getId());
        if (!messages.isEmpty()) {
            return ResponseEntity.ok(messages);
        } else
            return ResponseEntity.ok("You have not messages.");
    }

    // Show all messages from individual user by id:
    @GetMapping("/{id}")
    public ResponseEntity<?> messagesBySender(@PathVariable("id") Long senderId) {
        User recipient = cache.getUser();
        try {
            List<MessageModel> messages = messageService.getMessagesFromUser(senderId, recipient);
            return ResponseEntity.ok(messages);
        } catch (RequestRejectedException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
