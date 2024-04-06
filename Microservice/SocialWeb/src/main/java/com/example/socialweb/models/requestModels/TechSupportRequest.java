package com.example.socialweb.models.requestModels;

import com.example.socialweb.models.entities.User;
import lombok.Data;

import java.util.Date;

@Data
public class TechSupportRequest {
    private Long senderId;
    private String message;
    private String date;
    public TechSupportRequest(User user, MessageModel messageModel){
        senderId = user.getId();
        message = messageModel.getMessage();
        date = new Date(System.currentTimeMillis()).toString();
    }
    public TechSupportRequest(){

    }
}
