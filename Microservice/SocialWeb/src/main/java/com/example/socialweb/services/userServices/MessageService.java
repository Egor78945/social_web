package com.example.socialweb.services.userServices;

import com.example.socialweb.enums.ProfileCloseType;
import com.example.socialweb.exceptions.RequestCancelledException;
import com.example.socialweb.exceptions.WrongDataException;
import com.example.socialweb.exceptions.WrongFormatException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String HASH_KEY = "message";

    @Transactional
    public List<ProfileModel> getAllSendersMessage(Long recipientId) throws RequestCancelledException {
        List<ProfileModel> list = getAllByRecipientId(recipientId)
                .stream()
                .map(e -> UserConverter
                        .convertUserToProfileModel(e.getSender()))
                .distinct()
                .toList();
        if (list.isEmpty())
            throw new RequestCancelledException("You have not any messages.");
        return list;
    }

    @Transactional
    public void send(Long fromId, Long toId, MessageModel messageModel) throws RequestCancelledException, WrongFormatException {
        User to = userRepository.findUserById(toId);
        User from = userRepository.findUserById(fromId);
        if (from.getId().equals(toId))
            throw new RequestCancelledException("You can not sends messages to yourself.");
        else if (!MessageValidation.checkMessageValid(messageModel.getMessage()))
            throw new WrongFormatException("Size of this message is wrong.");
        else if (to.getCloseType() == ProfileCloseType.CLOSE)
            throw new RequestCancelledException("You can not send message to user with close profile.");
        else {
            from = userRepository.findUserById(from.getId());
            Message message = new Message.Builder(from, to)
                    .setMessage(messageModel.getMessage())
                    .build();
            messageRepository.save(message);
            log.info("Message has been sent.");
        }
    }

    @Transactional
    public List<Message> getAllByRecipientId(Long recipientId) throws RequestCancelledException {
        List<Object> messageHashes = redisTemplate.opsForHash().values(HASH_KEY);
        if (messageHashes.isEmpty()) {
            List<Message> messages = messageRepository.findAllByRecipient(userService.getUserById(recipientId));
            if (!messages.isEmpty()) {
                messages.forEach(m -> redisTemplate.opsForHash().put(HASH_KEY, m.getId(), MessageConverter.convertMessageToJsonString(m)));
                return messages;
            }
            throw new RequestCancelledException("You have not any messages.");
        }
        return messageHashes.stream().map(h -> MessageConverter.convertJsonToMessage((String) h)).toList();
    }

    @Transactional
    public List<Message> getAllBySenderAndRecipient(User sender, User recipient) {
        return messageRepository.findAllBySenderAndRecipient(sender, recipient);
    }

    @Transactional
    public List<MessageModel> getMessagesFromUser(Long senderId, Long recipientId) throws RequestCancelledException {
        User sender = userRepository.findUserById(senderId);
        User recipient = userRepository.findUserById(recipientId);
        List<MessageModel> messages = getAllBySenderAndRecipient(sender, recipient)
                .stream()
                .map(MessageConverter::convertMessageToMessageModel)
                .collect(Collectors.toList());
        if (!messages.isEmpty())
            return messages;
        else if (sender.getId().equals(recipient.getId()))
            throw new RequestCancelledException("You can not get messages from yourself.");
        else
            throw new RequestCancelledException("You have not got messages from this user.");
    }
}
