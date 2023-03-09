package com.infinity.employee;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestConfigurationHelper {
     String value() default "jdbc:postgresql://database:5432/public?slide=disable";

}
