package com.iris.system.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LogAspect {

    @Around("execution(* com.iris.messaging.controller.*.*(..))")
    public Object logRequestHandling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        boolean error = false;
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            error = true;
            throw exception;
        } finally {
            String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();
            long endTime = System.currentTimeMillis();
            log.info("{}.{} latency: {} ms, error: {}", className, methodName, endTime - startTime, error);
        }

    }
}
