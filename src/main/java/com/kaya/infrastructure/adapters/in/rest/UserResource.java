package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.dto.AbstractCreateUserDTO;
import com.kaya.application.service.UserService;
import com.kaya.domain.model.User;
import com.kaya.infrastructure.adapters.in.rest.error.ErrorHandler;
import com.kaya.infrastructure.adapters.in.rest.error.ErrorResponse;
import io.smallrye.mutiny.Uni;
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
import org.jboss.logging.Logger;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "User management operations")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    ErrorHandler errorHandler;

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
        ErrorResponse errorResponse = errorHandler.handleException(throwable);
        return Response.status(errorResponse.getStatus()).entity(errorResponse).build();
    }
}