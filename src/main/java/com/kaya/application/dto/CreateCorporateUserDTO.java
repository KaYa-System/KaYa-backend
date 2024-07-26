package com.kaya.application.dto;

import com.kaya.domain.model.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Data
@EqualsAndHashCode(callSuper = true)
@Input
@Description("DTO for creating a corporate user")
public class CreateCorporateUserDTO extends AbstractCreateUserDTO {
    @NotBlank(message = "Company name is required")
    @Description("Company name")
    private String companyName;

    @NotBlank(message = "Registration number is required")
    @Description("Registration number")
    private String registrationNumber;

    public CreateCorporateUserDTO() {
        this.setType(UserType.CORPORATE);
    }
}
