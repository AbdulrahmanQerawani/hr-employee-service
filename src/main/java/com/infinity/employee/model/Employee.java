package com.infinity.employee.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infinity.employee.utils.ExpressionValidator;
import com.infinity.employee.utils.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;


import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Employee extends RepresentationModel<Employee>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "identity_card")
    private String identityCard;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email_address", nullable = false)
    @NotBlank
    @Email(message = "Invalid Email", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String emailAddress;
    @Column(name = "birth_date",nullable = false)
    private Date birthDate;
    @Column(name = "hire_date")
    private Date hireDate;
    @Column(name = "department_id", nullable = false)
    private String unitId;
    @Transient
    private String unitName;
    @Transient
    private String unitDescription;
    @Version
    private Long version;

    public Employee(String firstName, String lastName, Gender gender, String identityCard, String phoneNumber, String emailAddress, Date birthDate, Date hireDate, String unitId, String unitName, String unitDescription) {
        if (!emailAddress.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}"))
            throw new RuntimeException("Invalid Email");
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.identityCard = identityCard;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.unitId = unitId;
        this.unitName = unitName;
        this.unitDescription = unitDescription;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        if (!ExpressionValidator.isValid(emailAddress, ExpressionValidator.EMAIL_REGEX))
            throw new RuntimeException(emailAddress+" is not valid email");
        this.emailAddress = emailAddress;
    }
}
