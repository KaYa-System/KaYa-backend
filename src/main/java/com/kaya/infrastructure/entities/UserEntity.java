package com.kaya.infrastructure.entities;

import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.domain.model.enums.UserType;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // Utiliser un nom de table différent pour éviter les conflits
@Description("Represents a user in the system")
public abstract class UserEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @NonNull
    @Description("Unique identifier for the user")
    protected UUID id;

    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @NonNull
    @Description("User's first name")
    protected String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    @NonNull
    @Description("User's last name")
    protected String lastName;

    @Column(unique = true, nullable = false)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @NonNull
    @Description("User's email address")
    protected String email;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Phone number is required")
    @NonNull
    @Description("User's phone number")
    protected String phoneNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "User type is required")
    @NonNull
    @Description("Type of user (INDIVIDUAL, CORPORATE)")
    protected UserType type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Authentication method is required")
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Description("Timestamp of when the user was created")
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Description("Timestamp of when the user was last updated")
    protected LocalDateTime updatedAt;
}
