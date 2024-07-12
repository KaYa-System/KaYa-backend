package com.kaya.application.dto;

import com.kaya.domain.model.enums.UserType;
import com.kaya.infrastructure.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Input
public class CreateUserDTO {

    @NotBlank(message = "Phone number is required")
    @PhoneNumber(message = "Invalid phone number")
    @Description("Phone number")
    private String phoneNumber;

    @NotNull(message = "User type is required")
    @Description("User type")
    private UserType type;

    // Optional fields for corporate users
    @Description("Company name")
    private String companyName;
    @Description("Registration number")
    private String registrationNumber;
}