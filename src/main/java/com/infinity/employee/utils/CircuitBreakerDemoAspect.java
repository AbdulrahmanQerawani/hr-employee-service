package com.infinity.employee.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@Aspect
@Component
@Slf4j
public class CircuitBreakerDemoAspect {
    @Around("@annotation(com.infinity.employee.utils.CircuitBreakerDemo)")
    public Object processCircuitBreakerDemo(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CircuitBreakerDemo circuitBreakerDemo = method.getAnnotation(CircuitBreakerDemo.class);
        log.info("processCircuitBreakerDemoAnnotation -> Value: " + circuitBreakerDemo.value());
        randomlyRunLong(circuitBreakerDemo.value());
        return joinPoint.proceed();
    }
    private void randomlyRunLong(int ms){
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        if (randomNum==3) sleep(ms);
    }
    private void sleep(int ms){
        try {
            Thread.sleep(ms);
            throw new java.util.concurrent.TimeoutException();
        } catch (InterruptedException | TimeoutException e) {
            log.error("error from CircuitBreakerDemo ->",e.getMessage());
        }
    }
}