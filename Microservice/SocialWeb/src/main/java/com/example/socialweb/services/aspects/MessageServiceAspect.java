package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MessageServiceAspect {
    @After("execution(public java.util.List<com.example.socialweb.models.responseModels.ProfileModel> getAllSendersMessage(java.lang.Long))")
    public void afterGetAllSendersByRecipientIdAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got all users, sends messages to user with id %s", args[0]));
    }

    @After("execution(public void send(java.lang.Long, java.lang.Long, com.example.socialweb.models.requestModels.MessageModel))")
    public void afterSendMessageAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object o : args) {
            if (o instanceof MessageModel) {
                log.info(String.format("User with id %s sent message to user with id %s: %s", args[0], args[1], ((MessageModel) o).getMessage()));
            }
        }
    }

    @After("execution(public java.util.List<com.example.socialweb.models.entities.Message> getAllByRecipientId(java.lang.Long))")
    public void afterGetAllMessagesByRecipientIdAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got all messages to user with id %s", args[0]));
    }

    @After("execution(public java.util.List<com.example.socialweb.models.entities.Message> getAllBySenderAndRecipient(java.lang.Long, java.lang.Long))")
    public void afterGetAllMessagesBySenderIdAndRecipientIdAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got all messages to user with id %s from user with id %s.", args[1], args[0]));
    }
}
