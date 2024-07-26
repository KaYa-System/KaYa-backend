package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.dto.AbstractCreateUserDTO;
import com.kaya.application.service.UserService;
import com.kaya.domain.exception.EmailAlreadyInUseException;
import com.kaya.domain.exception.PhoneNumberAlreadyInUseException;
import com.kaya.domain.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

import org.jboss.logging.Logger;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "User management operations")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    Logger logger;

    @POST
    @Operation(summary = "Create a new user", description = "Creates a new user with the given details")
    @ApiResponse(responseCode = "201", description = "User created successfully",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public Uni<Response> createUser(@Valid @Parameter(description = "User details to be created", required = true) AbstractCreateUserDTO createUserDTO) {
        logger.info("Received request to create user");
        return userService.createUser(createUserDTO)
                .onItem().transform(user -> Response.status(Response.Status.CREATED).entity(user).build())
                .onFailure().recoverWithItem(this::handleCreateUserFailure);
    }

    private Response handleCreateUserFailure(Throwable throwable) {
        logger.error("Failed to create user", throwable);
        if (throwable instanceof EmailAlreadyInUseException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiExceptionHandler.ErrorResponse("Email already in use", "EMAIL_ALREADY_IN_USE"))
                    .build();
        } else if (throwable instanceof PhoneNumberAlreadyInUseException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiExceptionHandler.ErrorResponse("Phone number already in use", "PHONE_NUMBER_ALREADY_IN_USE"))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ApiExceptionHandler.ErrorResponse(throwable.getMessage(), "USER_CREATION_FAILED"))
                    .build();
        }
    }
}
