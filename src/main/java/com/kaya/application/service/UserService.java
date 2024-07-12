package com.kaya.application.service;

import com.kaya.application.dto.UserProfileDTO;
import com.kaya.application.port.in.user.*;
import com.kaya.application.port.out.UserRepository;
import com.kaya.domain.model.User;
import com.kaya.domain.model.IndividualUser;
import com.kaya.domain.model.CorporateUser;
import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.domain.model.enums.UserType;
import com.kaya.infrastructure.external.OTPService;
import com.kaya.infrastructure.external.ExternalAuthService;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
class UserService implements CreateUserUseCase, VerifyPhoneNumberUseCase, SetUserPinUseCase, AuthenticateUserUseCase, CompleteUserProfileUseCase {

    @Inject
    UserRepository userRepository;

    @Inject
    OTPService otpService;

    @Inject
    ExternalAuthService externalAuthService;

    @Override
    public Uni<String> authenticateWithPin(String phoneNumber, String pinCode) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .onItem().transform(Unchecked.function(user -> {
                    if (user instanceof IndividualUser && ((IndividualUser) user).getPinCode().equals(pinCode)) {
                        // Générer et retourner un token JWT
                        return generateJwtToken(user);
                    } else {
                        throw new SecurityException("Invalid credentials");
                    }
                }));
    }

    @Override
    public Uni<String> authenticateWithExternalProvider(AuthMethod authMethod, String externalToken) {
        return externalAuthService.validateToken(authMethod, externalToken)
                .onItem().transformToUni(externalId -> userRepository.findByExternalId(authMethod, externalId))
                .onItem().transform(this::generateJwtToken);
    }

    @Override
    public Uni<User> completeProfile(UUID userId, UserProfileDTO profileData) {
        return userRepository.findById(userId)
                .onItem().transform(user -> {
                    updateUserFromProfileData(user, profileData);
                    return user;
                })
                .onItem().transformToUni(userRepository::save);
    }

    @Override
    public Uni<User> createIndividualUser(String phoneNumber, UserType type) {
        IndividualUser user = new IndividualUser();
        user.setPhoneNumber(phoneNumber);
        user.setType(type);
        user.setAuthMethod(AuthMethod.PHONE);
        return userRepository.save(user);
    }

    @Override
    public Uni<User> createIndividualUserWithExternalAuth(String email, UserType type, AuthMethod authMethod, String externalId) {
        IndividualUser user = new IndividualUser();
        user.setEmail(email);
        user.setType(type);
        user.setAuthMethod(authMethod);
        user.setExternalId(externalId);
        return userRepository.save(user);
    }

    @Override
    public Uni<User> createCorporateUser(String email, UserType type, String companyName, String companyRegistrationNumber) {
        CorporateUser user = new CorporateUser();
        user.setEmail(email);
        user.setType(type);
        user.setCompanyName(companyName);
        user.setCompanyRegistrationNumber(companyRegistrationNumber);
        return userRepository.save(user);
    }

    @Override
    public Uni<User> setPinCode(UUID userId, String pinCode) {
        return userRepository.findById(userId)
                .onItem().transform(Unchecked.function(user -> {
                    if (user instanceof IndividualUser) {
                        ((IndividualUser) user).setPinCode(pinCode);
                        return user;
                    } else {
                        throw new IllegalArgumentException("PIN can only be set for individual users");
                    }
                }))
                .onItem().transformToUni(userRepository::save);
    }

    @Override
    public Uni<Boolean> sendOTP(String phoneNumber) {
        return otpService.generateAndSendOTP(phoneNumber);
    }

    @Override
    public Uni<Boolean> verifyOTP(String phoneNumber, String otp) {
        return otpService.verifyOTP(phoneNumber, otp)
                .onItem().transformToUni(isValid -> {
                    if (isValid) {
                        return userRepository.findByPhoneNumber(phoneNumber)
                                .onItem().transform(user -> {
                                    user.setVerified(true);
                                    return user;
                                })
                                .onItem().transformToUni(userRepository::save)
                                .onItem().transform(user -> true);
                    } else {
                        return Uni.createFrom().item(false);
                    }
                });
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