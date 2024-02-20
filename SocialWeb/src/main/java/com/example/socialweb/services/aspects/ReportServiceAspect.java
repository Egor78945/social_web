package com.example.socialweb.services.aspects;

import com.example.socialweb.models.requestModels.ReportModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ReportServiceAspect {
    @Before("execution(public void reportUser(com.example.socialweb.models.entities.User, com.example.socialweb.models.entities.User, com.example.socialweb.models.requestModels.ReportModel))")
    public void beforeReportAdvice(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        for(Object o: arguments){
            if(o instanceof ReportModel){
                log.info("Attempt to report user with id " + ((ReportModel) o).getUserId() + ". Reason - " + ((ReportModel) o).getReason() + "...");
            }
        }
    }
}
