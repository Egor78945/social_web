package com.example.socialweb.controllers.userControllers;

import com.example.socialweb.annotations.customExceptionHandlers.UserControllersExceptionHandler;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.exceptions.WrongFormatException;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.services.userServices.MessageService;
import com.example.socialweb.services.userServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@UserControllersExceptionHandler
@Slf4j
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    // Send message to any user by id:
    @PostMapping("/{id}")
    public ResponseEntity<String> sendMessage(@PathVariable("id") Long toId, @RequestBody MessageModel messageModel) throws RequestCancelledException, WrongFormatException {
        messageService.send(userService.getCurrentUser().getId(), toId, messageModel);
        return ResponseEntity.ok("Message has been sent.");
    }

    // Show all users, who wrote any messages to me
    @GetMapping
    public ResponseEntity<?> mySenders() throws RequestCancelledException {
        List<ProfileModel> messages = messageService.getAllSendersMessage(userService.getCurrentUser().getId());
        return ResponseEntity.ok(messages);
    }

    // Show all messages from individual user by id:
    @GetMapping("/{id}")
    public ResponseEntity<?> messagesBySender(@PathVariable("id") Long senderId) throws RequestCancelledException {
        List<MessageModel> messages = messageService.getMessagesFromUser(senderId, userService.getCurrentUser().getId());
        return ResponseEntity.ok(messages);
    }
}
