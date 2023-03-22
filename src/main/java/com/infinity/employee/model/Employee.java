package com.infinity.employee.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinity.employee.common.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter @Setter @ToString @NoArgsConstructor @EqualsAndHashCode
@Entity
@Table(name = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Employee extends RepresentationModel<Employee>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "age")
    private int age;

    @Column(name = "email_address")
    @Email
    private String emailAddress;

    @Column(name = "organization_id")
    private Long organizationId;

    @Transient
    private String organizationName;

    @Column(name = "department_id")
    private Long departmentId;

    @Transient
    private String departmentName;

    @Column(name = "position")
    private String position;

    @Version
    private Integer version;

    public Employee(String firstName, String lastName, Gender gender, String emailAddress) {
        if (!emailAddress.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}"))
            throw new RuntimeException("Invalid Email");
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.emailAddress = emailAddress;
    }

    public Employee(Long departmentId, String firstName, String lastName, Gender gender, String emailAddress, String position) {
        this.departmentId = departmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.emailAddress = emailAddress;
        this.position = position;
    }
    public Employee(Long employeeId,Long departmentId, String firstName, String lastName, Gender gender, String emailAddress, String position) {
        this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.emailAddress = emailAddress;
        this.position = position;
    }
}
