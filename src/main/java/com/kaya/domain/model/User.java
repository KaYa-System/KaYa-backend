package com.kaya.domain.model;
import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.domain.model.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public abstract class User {

    protected UUID id;

    protected String firstName;

    protected String lastName;


    protected String email;


    protected String phoneNumber;


    protected UserType type;


    protected AuthMethod authMethod;

    protected String externalId;

    protected boolean isVerified;

    protected LocalDate dateOfBirth;

    protected String nationality;

    protected String preferredLanguage;

    protected String profilePictureUrl;

    protected String bio;

    protected boolean emailNotifications;

    protected boolean smsNotifications;

    protected boolean pushNotifications;

    protected String preferredCity;

    protected String preferredPropertyType;

    protected String preferredAmenities;
}
