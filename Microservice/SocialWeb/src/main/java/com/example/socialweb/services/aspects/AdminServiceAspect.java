package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.BanModel;
import jakarta.persistence.Column;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AdminServiceAspect {
    @After("execution(public void banUser(com.example.socialweb.models.requestModels.BanModel, com.example.socialweb.models.entities.User))")
    public void afterBanUserAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        User admin = (User) args[1];
        BanModel banModel = (BanModel) args[0];
        log.info(String.format("Admin with id %s banned user with id %s by reason: %s", admin.getId(), banModel.getUserId(), banModel.getReason()));
    }

    @After("execution(public void unbanUser(java.lang.Long))")
    public void afterUnbanUserAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s has been unbanned.", args[0]));
    }
}
