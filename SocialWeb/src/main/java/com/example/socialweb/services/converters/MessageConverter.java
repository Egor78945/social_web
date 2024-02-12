package com.example.socialweb.services.converters;

import com.example.socialweb.models.entities.Message;
import com.example.socialweb.models.entities.User;

import java.util.ArrayList;
import java.util.List;

public class MessageConverter {
    public static User convertMessageToSender(Message message){
        return message.getSender();
    }
    public static List<User> convertMessageToSender(List<Message> list){
        List<User> senders = new ArrayList<>();
        for(Message m: list){
            senders.add(convertMessageToSender(m));
        }
        return senders;
    }
}
