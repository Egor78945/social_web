package com.example.socialweb.exceptions;

public class WrongUserDataException extends RuntimeException{
    public WrongUserDataException(String msg){
        super(msg);
    }
}
