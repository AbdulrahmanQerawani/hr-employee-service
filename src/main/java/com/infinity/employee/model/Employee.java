package com.infinity.employee.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinity.employee.utils.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


import java.util.Date;

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
    @Column(name = "email_address")
//    @NotBlank(message = "employee.add.error.email.message")
    @Email(message = "employee.add.error.email.message", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String emailAddress;
    @Transient
    private String unitName;
    @Transient
    private String unitDescription;
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

    public Employee(Long employeeId, String firstName, String lastName, Gender gender, String emailAddress) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.emailAddress = emailAddress;
    }
}
