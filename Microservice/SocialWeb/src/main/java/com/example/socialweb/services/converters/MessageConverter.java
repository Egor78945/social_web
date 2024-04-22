package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.requestModels.TechSupportRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageConverter {
    public static User convertMessageToSender(Message message) {
        return message.getSender();
    }

    public static List<User> convertMessageToSender(List<Message> list) {
        return list
                .stream()
                .map(MessageConverter::convertMessageToSender)
                .distinct()
                .collect(Collectors.toList());
    }

    public static MessageModel convertMessageToMessageModel(Message message) {
        return new MessageModel(message);
    }

    public static List<MessageModel> convertMessageToMessageModel(List<Message> messages) {
        return messages
                .stream()
                .map(MessageConverter::convertMessageToMessageModel)
                .collect(Collectors.toList());
    }

    public static TechSupportRequest convertMessageToTechSupportRequest(User user, MessageModel messageModel) {
        return new TechSupportRequest(user, messageModel);
    }
}
