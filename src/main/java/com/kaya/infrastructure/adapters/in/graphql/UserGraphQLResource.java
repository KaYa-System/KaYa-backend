package com.kaya.infrastructure.adapters.in.graphql;

import com.kaya.application.dto.CreateUserDTO;
import com.kaya.application.dto.SetPinDTO;
import com.kaya.application.dto.UserProfileDTO;
import com.kaya.application.dto.VerifyPhoneDTO;
import com.kaya.application.port.in.user.*;
import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.UserType;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.*;

import jakarta.inject.Inject;
import java.util.UUID;

@GraphQLApi
public class UserGraphQLResource {

    @Inject
    CreateUserUseCase createUserUseCase;

    @Inject
    CompleteUserProfileUseCase completeUserProfileUseCase;

    @Inject
    VerifyPhoneNumberUseCase verifyPhoneNumberUseCase;

    @Inject
    SetUserPinUseCase setUserPinUseCase;

    @Mutation
    @Description("Create a new user")
    public Uni<User> createUser(@Name("user") CreateUserDTO createUserDTO) {
        if (createUserDTO.getType() == UserType.BUYER || createUserDTO.getType() == UserType.SELLER || createUserDTO.getType() == UserType.TENANT || createUserDTO.getType() == UserType.LANDLORD) {
            return createUserUseCase.createIndividualUser(
                    createUserDTO.getFirstName(),
                    createUserDTO.getLastName(),
                    createUserDTO.getEmail(),
                    createUserDTO.getPhoneNumber(),
                    createUserDTO.getType()
            );
        } else {
            return createUserUseCase.createCorporateUser(
                    createUserDTO.getFirstName(),
                    createUserDTO.getLastName(),
                    createUserDTO.getEmail(),
                    createUserDTO.getPhoneNumber(),
                    createUserDTO.getType(),
                    createUserDTO.getCompanyName(),
                    createUserDTO.getRegistrationNumber()
            );
        }
    }

    @Mutation
    @Description("Complete user profile")
    public Uni<User> completeProfile(@Name("userId") UUID userId, @Name("profile") UserProfileDTO profileDTO) {
        return completeUserProfileUseCase.completeProfile(userId, profileDTO);
    }

    @Mutation
    @Description("Verify phone number")
    public Uni<Boolean> verifyPhoneNumber(@Name("verifyPhone") VerifyPhoneDTO verifyPhoneDTO) {
        return verifyPhoneNumberUseCase.verifyOTP(verifyPhoneDTO.getPhoneNumber(), verifyPhoneDTO.getOtp());
    }

    @Mutation
    @Description("Set user PIN")
    public Uni<User> setUserPin(@Name("userId") UUID userId, @Name("pin") SetPinDTO setPinDTO) {
        return setUserPinUseCase.setPinCode(userId, setPinDTO.getPinCode());
    }
}
