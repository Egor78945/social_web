package com.example.socialweb.services.userServices;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.responseModels.ProfileModel;
import com.example.socialweb.repositories.MessageRepository;
import com.example.socialweb.repositories.UserRepository;
import com.example.socialweb.services.converters.MessageConverter;
import com.example.socialweb.services.converters.UserConverter;
import com.example.socialweb.services.validation.MessageValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String HASH_KEY = "message";

    @Transactional
    public List<ProfileModel> getAllSendersMessage(Long recipientId) throws WrongDataException {
        return getAllByRecipientId(recipientId)
                .stream()
                .map(e -> UserConverter
                        .convertUserToProfileModel(e.getSender()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public void send(Long fromId, Long toId, MessageModel messageModel) {
        User to = userRepository.findUserById(toId);
        User from = userRepository.findUserById(fromId);
        if (from.getId().equals(toId))
            throw new RequestRejectedException("You can not sends messages to yourself.");
        else if (!MessageValidation.checkMessageValid(messageModel.getMessage()))
            throw new RequestRejectedException("Size of this message is wrong.");
        else if (to.getCloseType() == ProfileCloseType.CLOSE)
            throw new RequestRejectedException("You can not send message to user with close profile.");
        else {
            from = userRepository.findUserById(from.getId());
            Message message = new Message.Builder(from, to)
                    .setMessage(messageModel.getMessage())
                    .build();
            messageRepository.save(message);
        }
    }

    @Transactional
    public List<Message> getAllByRecipientId(Long recipientId) throws WrongDataException {
        List<Object> messageHashes = redisTemplate.opsForHash().values(HASH_KEY);
        if (messageHashes.isEmpty()) {
            List<Message> messages = messageRepository.findAllByRecipient(userService.getUserById(recipientId));
            if (!messages.isEmpty()) {
                messages.forEach(m -> redisTemplate.opsForHash().put(HASH_KEY, m.getId(), MessageConverter.convertMessageToJsonString(m)));
                return messages;
            }
            throw new WrongDataException("You have not any messages.");
        }
        return messageHashes.stream().map(h -> MessageConverter.convertJsonToMessage((String) h)).toList();
    }

    @Transactional
    public List<Message> getAllBySenderAndRecipient(User sender, User recipient) {
        return messageRepository.findAllBySenderAndRecipient(sender, recipient);
    }

    @Transactional
    public List<MessageModel> getMessagesFromUser(Long senderId, Long recipientId) {
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        List<MessageModel> messages = getAllBySenderAndRecipient(sender, recipient)
                .stream()
                .map(MessageConverter::convertMessageToMessageModel)
                .collect(Collectors.toList());
        if (!messages.isEmpty())
            return messages;
        else if (sender.getId().equals(recipient.getId()))
            throw new RequestRejectedException("You can not get messages from yourself.");
        else
            throw new RequestRejectedException("You have not got messages from this user.");
    }
}
