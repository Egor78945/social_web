package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.requestModels.TechSupportRequest;

import java.util.ArrayList;
import java.util.List;

public class MessageConverter {
    public static User convertMessageToSender(Message message) {
        return message.getSender();
    }

    public static List<User> convertMessageToSender(List<Message> list) {
        List<User> senders = new ArrayList<>();
        for (Message m : list) {
            if (!senders.contains(convertMessageToSender(m)))
                senders.add(convertMessageToSender(m));
        }
        return senders;
    }

    public static MessageModel convertMessageToMessageModel(Message message) {
        return new MessageModel(message);
    }

    public static List<MessageModel> convertMessageToMessageModel(List<Message> message) {
        List<MessageModel> messageModels = new ArrayList<>();
        for (Message m : message) {
            messageModels.add(convertMessageToMessageModel(m));
        }
        return messageModels;
    }
    public static TechSupportRequest convertMessageToTechSupportRequest(User user, MessageModel messageModel){
        return new TechSupportRequest(user, messageModel);
    }
}
