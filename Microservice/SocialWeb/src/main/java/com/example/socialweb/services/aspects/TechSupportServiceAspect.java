package com.example.socialweb.services.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TechSupportServiceAspect {
    @After("execution(public void sendMessage(com.example.socialweb.models.requestModels.TechSupportRequest))")
    public void afterTechSupportMessageAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Message to tech support has been sent: %s", args[0]));
    }
}
