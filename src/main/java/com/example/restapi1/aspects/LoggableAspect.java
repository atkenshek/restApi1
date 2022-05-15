package com.example.restapi1.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggableAspect {
    @After(value = "@within(com.example.restapi1.annotations.Loggable) || @annotation(com.example.restapi1.annotations.Loggable)")
    public void logMethodSetupInfo(JoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().toString();
        String methodName = joinPoint.getSignature().getName();
        String params = joinPoint.getTarget().toString();
        log.info("target class = " + className);
        log.info("target method = " + methodName);
        log.info("method args = " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
        value = "@within(com.example.restapi1.annotations.Loggable) || @annotation(com.example.restapi1.annotations.Loggable)",
        returning = "result")
    public void logMethodReturnValue(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("returned value = " + result);
    }
}
