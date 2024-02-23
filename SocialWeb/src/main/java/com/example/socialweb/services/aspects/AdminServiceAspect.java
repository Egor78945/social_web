package com.example.socialweb.services.aspects;

import com.example.socialweb.models.requestModels.BanModel;
import jakarta.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AdminServiceAspect {
    @Before("execution(public void banUser(com.example.socialweb.models.requestModels.BanModel, com.example.socialweb.models.entities.User))")
    public void banUserAdvice(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object o: arguments){
            if(o instanceof BanModel){
                log.info("Attempt to ban user with id " + ((BanModel) o).getUserId() + ". Reason : " + ((BanModel) o).getReason() + ".");
            }
        }
    }
}
