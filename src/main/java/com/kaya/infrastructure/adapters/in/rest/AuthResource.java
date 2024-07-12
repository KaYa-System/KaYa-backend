package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.dto.LoginExternalDTO;
import com.kaya.application.dto.LoginPinDTO;
import com.kaya.application.dto.TokenResponseDTO;
import com.kaya.application.port.in.user.AuthenticateUserUseCase;
import com.kaya.domain.model.enums.AuthMethod;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    AuthenticateUserUseCase authenticateUserUseCase;

    @POST
    @Path("/login/pin")
    public Uni<Response> loginWithPin(LoginPinDTO loginDTO) {
        return authenticateUserUseCase.authenticateWithPin(loginDTO.getPhoneNumber(), loginDTO.getPinCode())
                .onItem().transform(token -> Response.ok().entity(new TokenResponseDTO(token)).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.UNAUTHORIZED).build());
    }

    @POST
    @Path("/login/external")
    public Uni<Response> loginWithExternalProvider(LoginExternalDTO loginDTO) {
        return authenticateUserUseCase.authenticateWithExternalProvider(loginDTO.getAuthMethod(), loginDTO.getExternalToken())
                .onItem().transform(token -> Response.ok().entity(new TokenResponseDTO(token)).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.UNAUTHORIZED).build());
    }
}