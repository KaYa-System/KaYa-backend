package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.dto.CreateUserDTO;
import com.kaya.application.dto.SetPinDTO;
import com.kaya.application.dto.VerifyPhoneDTO;
import com.kaya.application.port.in.user.*;
import com.kaya.application.dto.UserProfileDTO;
import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.UserType;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "User management operations")
public class UserResource {

    @Inject
    CreateUserUseCase createUserUseCase;

    @Inject
    CompleteUserProfileUseCase completeUserProfileUseCase;

    @Inject
    VerifyPhoneNumberUseCase verifyPhoneNumberUseCase;

    @Inject
    SetUserPinUseCase setUserPinUseCase;

    @POST
    @Operation(summary = "Create a new user", description = "Creates a new user with the given details")
    @APIResponse(responseCode = "201", description = "User created successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Uni<Response> createUser(
            @Parameter(description = "User details to be created", required = true) CreateUserDTO createUserDTO) {
        if (createUserDTO.getType() == UserType.BUYER || createUserDTO.getType() == UserType.SELLER || createUserDTO.getType() == UserType.TENANT || createUserDTO.getType() == UserType.LANDLORD) {
            return createUserUseCase.createIndividualUser(createUserDTO.getFirstName(), createUserDTO.getLastName(), createUserDTO.getEmail(), createUserDTO.getPhoneNumber(), createUserDTO.getType())
                    .onItem().transform(user -> Response.status(Response.Status.CREATED).entity(user).build());
        } else {
            return createUserUseCase.createCorporateUser(createUserDTO.getFirstName(), createUserDTO.getLastName(), createUserDTO.getEmail(), createUserDTO.getPhoneNumber(), createUserDTO.getType(), createUserDTO.getCompanyName(), createUserDTO.getRegistrationNumber())
                    .onItem().transform(user -> Response.status(Response.Status.CREATED).entity(user).build());
        }
    }

    @PUT
    @Path("/{userId}/profile")
    @Operation(summary = "Complete user profile", description = "Completes the profile of an existing user")
    @APIResponse(responseCode = "200", description = "Profile completed successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Uni<Response> completeProfile(
            @Parameter(description = "ID of the user whose profile is to be completed", required = true) @RestPath UUID userId,
            @Parameter(description = "Profile details to be updated", required = true) UserProfileDTO profileDTO) {
        return completeUserProfileUseCase.completeProfile(userId, profileDTO)
                .onItem().transform(user -> Response.ok(user).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.BAD_REQUEST).build());
    }

    @POST
    @Path("/verify-phone")
    @Operation(summary = "Verify phone number", description = "Verifies the user's phone number with OTP")
    @APIResponse(responseCode = "200", description = "Phone number verified successfully")
    @APIResponse(responseCode = "400", description = "Invalid OTP")
    public Uni<Response> verifyPhoneNumber(
            @Parameter(description = "Details for phone verification", required = true) VerifyPhoneDTO verifyPhoneDTO) {
        return verifyPhoneNumberUseCase.verifyOTP(verifyPhoneDTO.getPhoneNumber(), verifyPhoneDTO.getOtp())
                .onItem().transform(isValid -> isValid ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build());
    }

    @POST
    @Path("/{userId}/set-pin")
    @Operation(summary = "Set user PIN", description = "Sets a new PIN for the user")
    @APIResponse(responseCode = "200", description = "PIN set successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Uni<Response> setUserPin(
            @Parameter(description = "ID of the user whose PIN is to be set", required = true) @RestPath UUID userId,
            @Parameter(description = "PIN details to be set", required = true) SetPinDTO setPinDTO) {
        return setUserPinUseCase.setPinCode(userId, setPinDTO.getPinCode())
                .onItem().transform(user -> Response.ok(user).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.BAD_REQUEST).build());
    }
}
