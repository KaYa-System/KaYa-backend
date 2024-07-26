package com.kaya.application.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.domain.model.enums.UserType;
import com.kaya.infrastructure.validation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Data
@Input
@Description("Abstract DTO for creating a user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateIndividualUserDTO.class, name = "INDIVIDUAL"),
        @JsonSubTypes.Type(value = CreateCorporateUserDTO.class, name = "CORPORATE")
})
public abstract class AbstractCreateUserDTO {

    @NotBlank(message = "Phone number is required")
    @PhoneNumber(message = "Invalid phone number")
    @Pattern(regexp = "^\\+225(07|05|01)[0-9]{8}$", message = "Invalid phone number format for Côte d'Ivoire")
    @Description("Phone number")
    private String phoneNumber;

    @NotNull(message = "User type is required")
    @Description("User type")
    private UserType type;

    @NotNull(message = "Auth Method is required")
    @Description("Auth Method")
    private AuthMethod authMethod;

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    @Description("First name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
    @Description("Last name")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    @Description("Email address")
    private String email;
}
