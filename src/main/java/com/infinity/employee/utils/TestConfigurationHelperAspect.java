package com.infinity.employee.utils;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.weaver.reflect.JoinPointMatchImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Configuration
@Slf4j
public class TestConfigurationHelperAspect {
    @Around("@annotation(com.infinity.employee.utils.TestConfigurationHelper)")
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/infinityhr_dev?sslmode=disable");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setSchema("hr");
        return dataSource;
    }

}