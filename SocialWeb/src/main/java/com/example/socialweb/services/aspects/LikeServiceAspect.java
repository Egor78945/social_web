package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.Like;
import com.example.socialweb.models.entities.News;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LikeServiceAspect {
    @Before("execution(public boolean like(com.example.socialweb.models.entities.Like, com.example.socialweb.models.entities.User))")
    public void beforeLikeNewsAdvice(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object o: arguments){
            if(o instanceof News){
                log.info("Attempt to like news - " + o.toString());
            }
        }
    }
}
