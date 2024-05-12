package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class NewsServiceAspect {
    @After("execution(public void postNews(com.example.socialweb.models.requestModels.NewsModel, java.lang.Long))")
    public void afterPostNewsAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s posted news.", args[1]));
    }

    @After("execution(public void deleteNews(java.lang.Long, java.lang.Long))")
    public void afterDeleteNewsAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("User with id %s deleted news with id %s", args[1], args[0]));
    }

    @After("execution(public java.util.List<com.example.socialweb.models.entities.News> getNewsByPublisherId(java.lang.Long))")
    public void afterGetNewsByPublisherIdAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got news with publisher id %s.", args[0]));
    }

    @After("execution(public java.util.List<com.example.socialweb.models.entities.News> getAllNews())")
    public void afterGetAllNewsAdvice() {
        log.info("Got all news.");
    }

    @After("execution(public com.example.socialweb.models.entities.News getNewsById(java.lang.Long))")
    public void afterGetNewsByIdAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info(String.format("Got news by id %s.", args[0]));
    }
}
