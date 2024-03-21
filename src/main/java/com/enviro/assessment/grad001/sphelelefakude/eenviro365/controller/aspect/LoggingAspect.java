package com.enviro.assessment.grad001.sphelelefakude.eenviro365.controller.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {
    }

    @Before("controller()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Received API request: {}", joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "controller()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Returned API response: {}", result);
    }

    @AfterThrowing(pointcut = "controller()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("Exception in API: {}", error.getMessage());
    }
}
