package com.vironit.onlinepharmacy.util.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getRootLogger();

    @Pointcut("execution(public * com.vironit.onlinepharmacy.service..*.*(..))")
    public void callAtService() {
    }

    @Before("callAtService()")
    public void beforeCallAt(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
       String className= joinPoint.getTarget()
               .getClass()
               .getName();
        String methodName= codeSignature.getName();
        LOGGER.info("Invoked method "+methodName+" in class "+className);
    }
}