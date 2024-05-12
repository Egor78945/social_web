package com.example.socialweb.services.aspects;

import com.example.socialweb.models.requestModels.ReportModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ReportServiceAspect {
    @After("execution(public void reportUser(java.lang.Long, java.lang.Long, com.example.socialweb.models.requestModels.ReportModel))")
    public void afterReportAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object o : args) {
            if (o instanceof ReportModel) {
                log.info(String.format("User with id %s reported user with id %s by reason: %s", args[0], ((ReportModel) o).getUserId(), ((ReportModel) o).getReason()));
            }
        }
    }
}
