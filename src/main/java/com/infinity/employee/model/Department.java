package com.infinity.employee.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter @Setter @ToString
public class Department extends RepresentationModel<Department> {
    private Long id;
    private String departmentName;
    private String departmentDescription;
}
