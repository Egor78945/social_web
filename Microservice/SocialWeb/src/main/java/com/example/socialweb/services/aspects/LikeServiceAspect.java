package com.example.socialweb.services.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LikeServiceAspect {
    @After("execution(public boolean like(java.lang.Long, java.lang.Long))")
    public void afterLikeAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s liked or unliked news with id %s.", args[1], args[0]));
    }
}
