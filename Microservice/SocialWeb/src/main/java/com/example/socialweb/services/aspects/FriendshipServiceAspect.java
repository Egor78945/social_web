package com.example.socialweb.services.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FriendshipServiceAspect {
    @Before("execution(public void friendRequest(com.example.socialweb.models.entities.User, com.example.socialweb.models.entities.User))")
    public void beforeSendFriendRequestAdvice(){
        log.info("Attempt to send friend request to the user...");
    }
    @Before("execution(public void confirmRequest(com.example.socialweb.models.entities.User, com.example.socialweb.models.entities.User))")
    public void beforeConfirmFriendRequestAdvice(){
        log.info("Attempt to confirm a friend request from the user...");
    }
    @Before("execution(public void rejectRequest(com.example.socialweb.models.entities.User, com.example.socialweb.models.entities.User))")
    public void beforeRejectFriendRequestAdvice(){
        log.info("Attempt to reject a friend request from the user...");
    }
    @Before("execution(public void removeFriend(com.example.socialweb.models.entities.User, com.example.socialweb.models.entities.User))")
    public void beforeRemoveFriendAdvice(){
        log.info("Attempt to remove the user from my friend list.");
    }
}
