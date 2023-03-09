package com.infinity.employee.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@RedisHash("organization")
public class Organization {
    @Id
    private Long id;
    private String name;
    private String address;
}
