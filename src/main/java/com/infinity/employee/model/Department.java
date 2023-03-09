package com.infinity.employee.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
@Getter @Setter @ToString
@RedisHash("department")
public class Department {
    @Id
    private Long id;
    private String name;
}
