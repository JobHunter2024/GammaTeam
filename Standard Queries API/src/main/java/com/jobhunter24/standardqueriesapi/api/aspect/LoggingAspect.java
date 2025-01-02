package com.jobhunter24.standardqueriesapi.api.aspect;

import ch.qos.logback.classic.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class.getName());

    @Around("execution(* com.jobhunter24.standardqueriesapi.api.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Method " + joinPoint.getSignature() + " called with args: " + Arrays.toString(joinPoint.getArgs()));

        Object result = null;
        try {

            result = joinPoint.proceed();

        } catch (Exception ex) {

            logger.error("Exception in method " + joinPoint.getSignature() + ": " + ex.getMessage());

            throw ex;
        } finally {

            long duration = System.currentTimeMillis() - start;
            logger.info("Method " + joinPoint.getSignature() + " completed in " + duration + " ms" +
                    (result != null ? " with result: " + result : " with no result due to exception"));
        }
        return result;
    }
}
