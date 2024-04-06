package com.example.socialweb.models.requestModels;

import com.example.socialweb.models.entities.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class MessageModel {
    private String message;
    public MessageModel(Message message){
        this.message = message.getMessage();
    }
    public MessageModel(){

    }
}
