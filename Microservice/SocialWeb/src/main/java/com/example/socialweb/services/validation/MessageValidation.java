package com.example.socialweb.services.validation;

public class MessageValidation {
    public static boolean checkMessageValid(String message){
        return !message.isEmpty() && message.length() <= 200;
    }
}
