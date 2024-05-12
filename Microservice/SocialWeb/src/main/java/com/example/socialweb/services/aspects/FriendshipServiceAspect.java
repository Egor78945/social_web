package com.example.socialweb.services.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FriendshipServiceAspect {
    @After("execution(public java.util.List<com.example.socialweb.models.responseModels.ProfileModel> getAllFriendRequests(java.lang.Long, java.lang.Boolean))")
    public void afterGetAllFriendRequestsToUserAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got all friend request with status %s to user with id %s.", args[0], args[1]));
    }

    @After("execution(public void friendRequest(java.lang.Long, java.lang.Long))")
    public void afterSendFriendRequestAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s sent friend request to user with id %s", args[0], args[1]));
    }

    @After("execution(public void confirmRequest(java.lang.Long, java.lang.Long))")
    public void afterConfirmFriendRequestAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s confirmed friend request from user with id %s.", args[1], args[0]));
    }

    @After("execution(public void rejectRequest(java.lang.Long, java.lang.Long))")
    public void afterRejectFriendRequestAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s rejected friend request from user with id %s.", args[1], args[0]));
    }

    @After("execution(public void removeFriend(java.lang.Long, java.lang.Long))")
    public void afterRemoveFriendAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s and user with id %s is not longer friends.", args[1], args[0]));
    }
}
