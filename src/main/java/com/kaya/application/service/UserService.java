package com.kaya.application.service;

import com.kaya.application.dto.*;
import com.kaya.application.port.in.user.*;
import com.kaya.application.port.out.UserRepository;
import com.kaya.domain.exception.DomainException;
import com.kaya.domain.model.CorporateUser;
import com.kaya.domain.model.IndividualUser;
import com.kaya.domain.model.User;
import com.kaya.infrastructure.external.OTPService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService implements UserUseCases {

    @Inject
    UserRepository userRepository;

    @Inject
    OTPService otpService;

    @Inject
    ModelMapper modelMapper;

    @Inject
    Logger logger;

    @Override
    @WithSession
    public Uni<User> createUser(AbstractCreateUserDTO dto) {
        logger.info("Creating user with DTO: " + dto);
        return Uni.createFrom().item(Unchecked.supplier(() -> mapDtoToUser(dto)))
                .onItem().transformToUni(user -> {
                    logger.info("User mapped from DTO: " + user);
                    return userRepository.save(user);
                })
                .onFailure().transform(e -> {
                    logger.error("Failed to create user", e);
                    return new DomainException(e.getMessage(), DomainException.ErrorCode.GENERAL_ERROR);
                });
    }

    @Override
    @WithSession
    public Uni<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    @WithSession
    public Uni<User> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    @WithSession
    public Uni<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @WithSession
    public Uni<List<User>> getUsers(int page, int size) {
        return userRepository.findPage(page, size);
    }
    @Override
    public Uni<User> completeProfile(UUID userId, UserProfileDTO profileData) {
        logger.info("Completing profile for user ID: " + userId);
        return userRepository.findById(userId)
                .onItem().ifNull().failWith(() -> {
                    logger.warn("User not found for ID: " + userId);
                    return new DomainException("User not found", DomainException.ErrorCode.ENTITY_NOT_FOUND);
                })
                .onItem().transform(user -> {
                    logger.info("Updating user profile: " + user);
                    updateUserFromProfileData(user, profileData);
                    return user;
                })
                .onItem().transformToUni(userRepository::save)
                .onFailure().transform(e -> {
                    logger.error("Failed to update profile", e);
                    return new DomainException("Failed to update profile", DomainException.ErrorCode.GENERAL_ERROR);
                });
    }

    @Override
    public Uni<User> setPinCode(UUID userId, String pinCode) {
        logger.info("Setting PIN code for user ID: " + userId);
        return userRepository.findById(userId)
                .onItem().ifNull().failWith(() -> {
                    logger.warn("User not found for ID: " + userId);
                    return new DomainException("User not found", DomainException.ErrorCode.ENTITY_NOT_FOUND);
                })
                .onItem().transformToUni(Unchecked.function(user -> {
                    if (user instanceof IndividualUser) {
                        ((IndividualUser) user).setPinCode(pinCode);
                        logger.info("PIN code set for user: " + user);
                        return userRepository.save(user);
                    } else {
                        logger.warn("PIN can only be set for individual users");
                        throw new DomainException("PIN can only be set for individual users", DomainException.ErrorCode.INVALID_INPUT);
                    }
                }));
    }

    @Override
    public Uni<Boolean> sendOTP(String phoneNumber) {
        logger.info("Sending OTP to phone number: " + phoneNumber);
        return otpService.generateAndSendOTP(phoneNumber)
                .onFailure().recoverWithUni(e -> {
                    logger.error("Failed to send OTP", e);
                    return Uni.createFrom().failure(new DomainException("Failed to send OTP", DomainException.ErrorCode.GENERAL_ERROR));
                });
    }

    @Override
    public Uni<Boolean> verifyOTP(String phoneNumber, String otp) {
        logger.info("Verifying OTP for phone number: " + phoneNumber);
        return otpService.verifyOTP(phoneNumber, otp)
                .onItem().transformToUni(isValid -> {
                    if (isValid) {
                        logger.info("OTP verified successfully for phone number: " + phoneNumber);
                        return userRepository.findByPhoneNumber(phoneNumber)
                                .onItem().ifNull().failWith(() -> {
                                    logger.warn("User not found for phone number: " + phoneNumber);
                                    return new DomainException("User not found", DomainException.ErrorCode.ENTITY_NOT_FOUND);
                                })
                                .onItem().transformToUni(user -> {
                                    user.setVerified(true);
                                    return userRepository.save(user)
                                            .onItem().transform(savedUser -> {
                                                logger.info("User verified and saved: " + savedUser);
                                                return true;
                                            });
                                });
                    } else {
                        logger.warn("OTP verification failed for phone number: " + phoneNumber);
                        return Uni.createFrom().item(false);
                    }
                })
                .onFailure().recoverWithUni(e -> {
                    logger.error("Failed to verify OTP", e);
                    return Uni.createFrom().failure(new DomainException("Failed to verify OTP", DomainException.ErrorCode.GENERAL_ERROR));
                });
    }

    private User mapDtoToUser(AbstractCreateUserDTO dto) {
        if (dto instanceof CreateCorporateUserDTO) {
            return modelMapper.map(dto, CorporateUser.class);
        } else if (dto instanceof CreateIndividualUserDTO) {
            return modelMapper.map(dto, IndividualUser.class);
        } else {
            throw new DomainException("Invalid DTO type", DomainException.ErrorCode.INVALID_INPUT);
        }
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

        logger.info("User profile updated: " + user);
    }
}
