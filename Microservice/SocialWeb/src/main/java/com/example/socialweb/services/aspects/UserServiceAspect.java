package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.LoginModel;
import com.example.socialweb.models.requestModels.RegisterModel;
import com.example.socialweb.models.requestModels.ReportModel;
import com.example.socialweb.services.converters.UserConverter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UserServiceAspect {
    @After("execution(public void registration(com.example.socialweb.models.requestModels.RegisterModel, org.springframework.security.crypto.password.PasswordEncoder))")
    public void AfterRegisterAdvice(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        for (Object o : arguments) {
            if (o instanceof RegisterModel) {
                log.info("User has been registered: " + UserConverter.serializeUserToJsonString((User) o));
            }
        }
    }

    @After("execution(public com.example.socialweb.models.entities.User getUserByEmail(java.lang.String))")
    public void afterGetUserAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got user with email %s.", args[0]));
    }

    @After("execution(public java.util.List<com.example.socialweb.models.entities.User> getAllUsers())")
    public void afterGetAllUsersAdvice() {
        log.info("Got all users.");
    }

    @After("execution(public com.example.socialweb.models.entities.User getUserById(java.lang.Long))")
    public void afterGetUserById(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got user with id %s.", args[0]));
    }

    @After("execution(public com.example.socialweb.models.entities.User getCurrentUser())")
    public void afterGetCurrentUser() {
        log.info("Got current user.");
    }

    @After("execution(public org.springframework.http.ResponseEntity<?> login(com.example.socialweb.models.requestModels.LoginModel))")
    public void afterGenerateJWTAdvice() {
        log.info("JWT successfully generated.");
    }
}
