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
import org.jboss.resteasy.reactive.RestPath;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    public Uni<Response> createUser(CreateUserDTO createUserDTO) {
        return createUserUseCase.createIndividualUser(createUserDTO.getPhoneNumber(), createUserDTO.getType())
                .onItem().transform(user -> Response.status(Response.Status.CREATED).entity(user).build());
    }

    @PUT
    @Path("/{userId}/profile")
    public Uni<Response> completeProfile(@RestPath UUID userId, UserProfileDTO profileDTO) {
        return completeUserProfileUseCase.completeProfile(userId, profileDTO)
                .onItem().transform(user -> Response.ok(user).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.BAD_REQUEST).build());
    }

    @POST
    @Path("/verify-phone")
    public Uni<Response> verifyPhoneNumber(VerifyPhoneDTO verifyPhoneDTO) {
        return verifyPhoneNumberUseCase.verifyOTP(verifyPhoneDTO.getPhoneNumber(), verifyPhoneDTO.getOtp())
                .onItem().transform(isValid -> isValid ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build());
    }

    @POST
    @Path("/{userId}/set-pin")
    public Uni<Response> setUserPin(@RestPath UUID userId, SetPinDTO setPinDTO) {
        return setUserPinUseCase.setPinCode(userId, setPinDTO.getPinCode())
                .onItem().transform(user -> Response.ok(user).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.BAD_REQUEST).build());
    }
}