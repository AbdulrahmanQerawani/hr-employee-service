package com.infinity.employee.utils;

import org.springframework.context.annotation.ImportRuntimeHints;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.FIELD,ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CircuitBreakerDemo {
    int value() default 5000;
}
