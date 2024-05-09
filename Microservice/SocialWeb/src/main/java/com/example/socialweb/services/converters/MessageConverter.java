package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import com.example.socialweb.models.requestModels.TechSupportRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MessageConverter {
    private static final JsonMapper MAPPER = new JsonMapper();
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
    public static String convertMessageToJsonString(Message message){
        try {
            return MAPPER.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return null;
        }
    }
    public static Message convertJsonToMessage(String json){
        try {
            return MAPPER.readValue(json, Message.class);
        } catch (JsonProcessingException e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
