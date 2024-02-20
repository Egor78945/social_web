package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MessageServiceAspect {
    @Before("execution(public void send(com.example.socialweb.models.entities.User, com.example.socialweb.models.entities.User, com.example.socialweb.models.requestModels.MessageModel))")
    public void beforeSendMessageAdvice(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object o: arguments){
            if(o instanceof MessageModel){
                log.info("Attempt to send message - " + o.toString());
            }
        }
    }
}
