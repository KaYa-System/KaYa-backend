package com.kaya.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaya.domain.model.enums.AuthMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Data
@Input
@JsonInclude(JsonInclude.Include.NON_NULL)
@Description("DTO for updating a user")
public class UpdateUserDTO {

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+225(07|05|01)[0-9]{8}$", message = "Invalid phone number format for Côte d'Ivoire")
    @Description("Phone number")
    private String phoneNumber;

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

    @Description("Biography")
    private String bio;

    @Description("User's nationality")
    private String nationality;

    @Description("Preferred amenities")
    private String preferredAmenities;

    @Description("Preferred city")
    private String preferredCity;

    @Description("Preferred language")
    private String preferredLanguage;

    @Description("Preferred property type")
    private String preferredPropertyType;

    @Description("Profile picture URL")
    private String profilePictureUrl;

    @NotNull(message = "Email notifications preference is required")
    @Description("Email notifications preference")
    private Boolean emailNotifications;

    @NotNull(message = "Push notifications preference is required")
    @Description("Push notifications preference")
    private Boolean pushNotifications;

    @NotNull(message = "SMS notifications preference is required")
    @Description("SMS notifications preference")
    private Boolean smsNotifications;

    @NotNull(message = "Verification status is required")
    @Description("Verification status")
    private Boolean isVerified;
}
