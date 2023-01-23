package com.infinity.employee.model;

import com.infinity.employee.utils.ExpressionValidator;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Unit extends RepresentationModel<Unit> {
    private Long id;
    private String unitName;
    private String description;

    public void setUnitName(String unitName) {
        if (!ExpressionValidator.isValid(unitName, ExpressionValidator.NAME_REGEX)) {
            System.out.println("Invalid Name Exception");
            throw new RuntimeException("Invalid Name");
        }
        this.unitName = unitName;
    }
}
