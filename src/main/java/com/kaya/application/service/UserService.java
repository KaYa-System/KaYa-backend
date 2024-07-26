package com.kaya.application.service;

import com.kaya.application.dto.AbstractCreateUserDTO;
import com.kaya.application.dto.CreateCorporateUserDTO;
import com.kaya.application.dto.CreateIndividualUserDTO;
import com.kaya.application.dto.UserProfileDTO;
import com.kaya.application.port.in.user.*;
import com.kaya.application.port.out.UserRepository;
import com.kaya.domain.model.CorporateUser;
import com.kaya.domain.model.IndividualUser;
import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.domain.exception.DomainException;
import com.kaya.domain.exception.EmailAlreadyInUseException;
import com.kaya.domain.exception.PhoneNumberAlreadyInUseException;
import com.kaya.infrastructure.entities.CorporateUserEntity;
import com.kaya.infrastructure.entities.IndividualUserEntity;
import com.kaya.infrastructure.external.OTPService;
import com.kaya.infrastructure.external.ExternalAuthService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;


import java.util.UUID;

@ApplicationScoped
public class UserService implements CreateUserUseCase, VerifyPhoneNumberUseCase, SetUserPinUseCase, AuthenticateUserUseCase, CompleteUserProfileUseCase {

    @Inject
    UserRepository userRepository;

    @Inject
    OTPService otpService;

    @Inject
    ModelMapper modelMapper;

    @Inject
    Logger logger;
    @Inject
    ExternalAuthService externalAuthService;

    @Override
    public Uni<String> authenticateWithPin(String phoneNumber, String pinCode) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .onItem().transform(Unchecked.function(user -> {
                    if (user instanceof IndividualUser && ((IndividualUser) user).getPinCode().equals(pinCode)) {
                        return generateJwtToken(user);
                    } else {
                        throw new DomainException("Invalid credentials", DomainException.ErrorCode.UNAUTHORIZED_ACCESS);
                    }
                }));
    }

    @Override
    public Uni<String> authenticateWithExternalProvider(AuthMethod authMethod, String externalToken) {
        return externalAuthService.validateToken(authMethod, externalToken)
                .onItem().transformToUni(externalId -> userRepository.findByExternalId(authMethod, externalId))
                .onItem().transform(this::generateJwtToken)
                .onFailure().transform(e -> new DomainException("Authentication failed", DomainException.ErrorCode.UNAUTHORIZED_ACCESS));
    }

    @Override
    public Uni<User> completeProfile(UUID userId, UserProfileDTO profileData) {
        return userRepository.findById(userId)
                .onItem().ifNull().failWith(() -> new DomainException("User not found", DomainException.ErrorCode.ENTITY_NOT_FOUND))
                .onItem().transform(user -> {
                    updateUserFromProfileData(user, profileData);
                    return user;
                })
                .onItem().transformToUni(userRepository::save)
                .onFailure().transform(e -> new DomainException("Failed to update profile", DomainException.ErrorCode.GENERAL_ERROR));
    }

    @Override
    @WithSession
    public Uni<User> createUser(AbstractCreateUserDTO dto) {
        return Uni.createFrom().item(Unchecked.supplier(() -> {
                    User user;
                    if (dto instanceof CreateCorporateUserDTO) {
                        user = modelMapper.map(dto, CorporateUser.class);
                    } else if (dto instanceof CreateIndividualUserDTO) {
                        user = modelMapper.map(dto, IndividualUser.class);
                    } else {
                        throw new DomainException("Invalid DTO type", DomainException.ErrorCode.INVALID_INPUT);
                    }
                    return user;
                })).onItem().transformToUni(userRepository::save)
                .onFailure().transform(e -> new DomainException(e.getMessage(), DomainException.ErrorCode.GENERAL_ERROR));
    }


    @Override
    public Uni<User> setPinCode(UUID userId, String pinCode) {
        return userRepository.findById(userId)
                .onItem().ifNull().failWith(() -> new DomainException("User not found", DomainException.ErrorCode.ENTITY_NOT_FOUND))
                .onItem().transformToUni(Unchecked.function(user -> {
                    if (user instanceof IndividualUser) {
                        ((IndividualUser) user).setPinCode(pinCode);
                        return userRepository.save(user);
                    } else {
                        throw new DomainException("PIN can only be set for individual users", DomainException.ErrorCode.INVALID_INPUT);
                    }
                }));
    }

    @Override
    public Uni<Boolean> sendOTP(String phoneNumber) {
        return otpService.generateAndSendOTP(phoneNumber)
                .onFailure().recoverWithUni(e -> Uni.createFrom().failure(new DomainException("Failed to send OTP", DomainException.ErrorCode.GENERAL_ERROR)));
    }

    @Override
    public Uni<Boolean> verifyOTP(String phoneNumber, String otp) {
        return otpService.verifyOTP(phoneNumber, otp)
                .onItem().transformToUni(isValid -> {
                    if (isValid) {
                        return userRepository.findByPhoneNumber(phoneNumber)
                                .onItem().ifNull().failWith(() -> new DomainException("User not found", DomainException.ErrorCode.ENTITY_NOT_FOUND))
                                .onItem().transformToUni(user -> {
                                    user.setVerified(true);
                                    return userRepository.save(user)
                                            .onItem().transform(savedUser -> true);
                                });
                    } else {
                        return Uni.createFrom().item(false);
                    }
                })
                .onFailure().recoverWithUni(e -> Uni.createFrom().failure(new DomainException("Failed to verify OTP", DomainException.ErrorCode.GENERAL_ERROR)));
    }

    private String generateJwtToken(User user) {
        // Implement JWT token generation logic here
        return "generated.jwt.token";
    }

    private void updateUserFromProfileData(User user, UserProfileDTO profileData) {
        user.setFirstName(profileData.getFirstName());
        user.setLastName(profileData.getLastName());
        user.setEmail(profileData.getEmail());
        user.setPhoneNumber(profileData.getPhoneNumber());
        user.setDateOfBirth(profileData.getDateOfBirth());
        user.setNationality(profileData.getNationality());
        user.setPreferredLanguage(profileData.getPreferredLanguage());
        user.setProfilePictureUrl(profileData.getProfilePictureUrl());
        user.setBio(profileData.getBio());

        if (user instanceof CorporateUser corporateUser) {
            corporateUser.setCompanyName(profileData.getCompanyName());
            corporateUser.setCompanyRegistrationNumber(profileData.getCompanyRegistrationNumber());
            corporateUser.setJobTitle(profileData.getJobTitle());
        }

        // Set notification preferences
        user.setEmailNotifications(profileData.isEmailNotifications());
        user.setSmsNotifications(profileData.isSmsNotifications());
        user.setPushNotifications(profileData.isPushNotifications());

        // Set search preferences
        user.setPreferredCity(profileData.getPreferredCity());
        user.setPreferredPropertyType(profileData.getPreferredPropertyType());
        user.setPreferredAmenities(profileData.getPreferredAmenities());
    }

}
