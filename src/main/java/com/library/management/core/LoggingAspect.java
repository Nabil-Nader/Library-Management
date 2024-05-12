package com.library.management.core;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {


    @Before("execution(* com.library.management.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Method '{}' is about to be executed.", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.library.management.service.*.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception thrown in method '{}': {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
}