package com.example.socialweb.services.aspects;

import com.example.socialweb.models.entities.News;
import com.example.socialweb.models.entities.User;
import com.example.socialweb.models.requestModels.NewsModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class NewsServiceAspect {
    @Before("execution(public void postNews(com.example.socialweb.models.requestModels.NewsModel, com.example.socialweb.models.entities.User))")
    public void beforePostNewsAdvice(JoinPoint joinPoint) {
        Object[] arguments = joinPoint.getArgs();
        for (Object o : arguments) {
            if (o instanceof NewsModel) {
                log.info("Attempt to post the news: " + o.toString());
            } else if (o instanceof User)
                log.info("Publisher id - " + ((User) o).getId());
        }
    }
    @Before("execution(public void deleteNews(com.example.socialweb.models.entities.News, com.example.socialweb.models.entities.User))")
    public void beforeDeleteNewsAdvice(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object o: arguments){
            if(o instanceof News){
                log.info("Attempt to delete news with id " + ((News) o).getId() + ".");
            }
        }
    }
}
