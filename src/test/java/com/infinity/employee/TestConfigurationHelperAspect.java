package com.infinity.employee;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Method;

@Aspect
@Slf4j
public class TestConfigurationHelperAspect {
    String dataSourceUrl;
    @Around("@annotation(com.infinity.employee.TestConfigurationHelper)")
    public Object processTestConfigurationHelper(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        TestConfigurationHelper testConfigurationHelper = method.getAnnotation(TestConfigurationHelper.class);
        log.info("processTestConfigurationHelper -> Value: " + testConfigurationHelper.value());
        dataSourceUrl = testConfigurationHelper.value();
        return joinPoint.proceed();
    }
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceUrl);
        dataSource.setPassword("postgres");
        dataSource.setUsername("postgres");
        return dataSource;
    }
}