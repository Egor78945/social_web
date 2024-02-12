package com.example.socialweb.services;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.repositories.MessageRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.validation.MessageValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public void send(User from, User to, MessageModel messageModel) {
        if (from.getId().equals(to.getId()))
            throw new RequestRejectedException("You can not sends messages to yourself.");
        else if (!MessageValidation.checkMessageValid(messageModel.getMessage()))
            throw new RequestRejectedException("Size of this message is wrong.");
        else if (to.getCloseType() == ProfileCloseType.CLOSE)
            throw new RequestRejectedException("You can not send message to user with close profile.");
        else {
            User from1 = userRepository.findUserByEmail(from.getEmail());
            Message message = new Message.Builder(from1, to)
                    .setMessage(messageModel.getMessage())
                    .setSendDate(new Date(System.currentTimeMillis()).toString())
                    .build();
            messageRepository.save(message);
        }
    }
    public List<Message> getAllByRecipient(User recipient){
        return messageRepository.findAllByRecipient(recipient);
    }
}
