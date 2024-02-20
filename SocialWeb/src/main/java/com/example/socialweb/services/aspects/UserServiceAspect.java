package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.models.requestModels.ReportModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UserServiceAspect {
    @Before("execution(public void registration(com.example.socialweb.models.requestModels.RegisterModel, org.springframework.security.crypto.password.PasswordEncoder))")
    public void beforeRegisterAdvice(JoinPoint joinPoint){
        log.info("Attempt to register the user...");
        Object[] arguments = joinPoint.getArgs();
        for(Object o: arguments){
            if (o instanceof RegisterModel){
                log.info("Register user data: " + o.toString());
            }
        }
    }
}
