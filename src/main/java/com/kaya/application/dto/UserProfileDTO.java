package com.kaya.application.dto;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import com.kaya.infrastructure.validation.PhoneNumber;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Input("UserProfile")
@Description("User profile information")
public class UserProfileDTO {

    @Description("User's first name")
    private String firstName;

    @Description("User's last name")
    private String lastName;

    @Email(message = "Invalid email format")
    @Description("User's email address")
    private String email;

    @PhoneNumber(message = "Invalid phone number")
    @Description("User's phone number")
    private String phoneNumber;

    @Past(message = "Date of birth must be in the past")
    @Description("User's date of birth")
    private LocalDate dateOfBirth;

    @Description("User's nationality")
    private String nationality;

    @Description("User's preferred language")
    private String preferredLanguage;

    @Description("URL of the user's profile picture")
    private String profilePictureUrl;

    @Description("User's bio or description")
    private String bio;

    // Pour les utilisateurs professionnels (CorporateUser)
    @Description("Company name for corporate users")
    private String companyName;

    @Description("Company registration number for corporate users")
    private String companyRegistrationNumber;

    @Description("Job title for corporate users")
    private String jobTitle;

    // Préférences de notification
    @Description("Whether the user wants to receive email notifications")
    private boolean emailNotifications;

    @Description("Whether the user wants to receive SMS notifications")
    private boolean smsNotifications;

    @Description("Whether the user wants to receive push notifications")
    private boolean pushNotifications;

    // Préférences de recherche
    @Description("User's preferred city for property searches")
    private String preferredCity;

    @Description("User's preferred property type for searches")
    private String preferredPropertyType;

    @Description("User's preferred amenities for property searches")
    private String preferredAmenities;
}