package com.kaya.domain.model;

import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.domain.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.NonNull;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Description("Represents a user in the system")
public abstract class User {
    @Id
    @GeneratedValue
    @NonNull
    @Description("Unique identifier for the user")
    protected UUID id;

    @Column(nullable = false)
    @NonNull
    @Description("User's first name")
    protected String firstName;

    @Column(nullable = false)
    @NonNull
    @Description("User's last name")
    protected String lastName;

    @Column(unique = true, nullable = false)
    @NonNull
    @Description("User's email address")
    protected String email;

    @Column(unique = true)
    @NonNull
    @Description("User's phone number")
    protected String phoneNumber;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Description("Type of user (BUYER, SELLER, TENANT, LANDLORD)")
    protected UserType type;

    @Enumerated(EnumType.STRING)
    @NonNull
    @Description("Authentication method used by the user")
    protected AuthMethod authMethod;

    @Description("External ID for users authenticated through external providers")
    protected String externalId;

    @Description("Whether the user's account is verified")
    protected boolean isVerified;

    @Description("User's date of birth")
    protected LocalDate dateOfBirth;

    @Description("User's nationality")
    protected String nationality;

    @Description("User's preferred language")
    protected String preferredLanguage;

    @Description("URL of the user's profile picture")
    protected String profilePictureUrl;

    @Column(length = 1000)
    @Description("User's bio or description")
    protected String bio;

    @Description("Whether the user wants to receive email notifications")
    protected boolean emailNotifications;

    @Description("Whether the user wants to receive SMS notifications")
    protected boolean smsNotifications;

    @Description("Whether the user wants to receive push notifications")
    protected boolean pushNotifications;

    @Description("User's preferred city for property searches")
    protected String preferredCity;

    @Description("User's preferred property type for searches")
    protected String preferredPropertyType;

    @Description("User's preferred amenities for property searches")
    protected String preferredAmenities;
}
