package com.infinity.employee.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinity.employee.utils.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter @Setter @ToString
@Entity
@Table(name = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee extends RepresentationModel<Employee>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "identity_card")
    private String identityCard;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "hire_date")
    private Date hireDate;
    @Column(name = "department_id", nullable = false)
    private String departmentId;
    @Transient
    private String departmentName;
    @Transient
    private String departmentDescription;

}
