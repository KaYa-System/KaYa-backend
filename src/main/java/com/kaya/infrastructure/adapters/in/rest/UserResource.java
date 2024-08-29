package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.dto.AbstractCreateUserDTO;
import com.kaya.application.dto.UpdateUserDTO;
import com.kaya.application.service.UserService;
import com.kaya.domain.exception.DomainException;
import com.kaya.domain.model.User;
import com.kaya.infrastructure.adapters.in.rest.error.ErrorHandler;
import com.kaya.infrastructure.adapters.in.rest.error.ErrorResponse;
import com.kaya.infrastructure.validation.PhoneNumber;
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

import java.util.UUID;

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

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a user by ID", description = "Retrieves a user based on its ID")
    public Uni<Response> getUserById(@Valid @Parameter(description = "ID of the user to be retrieved", required = true) @PathParam("id") UUID id) {
        return userService.getUserById(id)
                .onItem().ifNotNull().transform(user -> Response.ok(user).build());
    }

    @GET
    @Path("/phoneNumber/{phoneNumber}")
    @Operation(summary = "Get a user by phone number", description = "Retrieves a user based on its phone number")
    public Uni<Response> getUserByPhoneNumber(@Valid @Parameter(description = "Phone number of the user to be retrieved", required = true) @PathParam("phoneNumber") @PhoneNumber  String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber)
                .onItem().ifNotNull().transform(user -> Response.ok(user).build());
    }

    @GET
    @Path("/email/{email}")
    @Operation(summary = "Get a user by email", description = "Retrieves a user based on its email")
    public Uni<Response> getUserByEmail(@Valid @Parameter(description = "Email of the user to be retrieved", required = true) @PathParam("email") String email) {
        return userService.getUserByEmail(email)
                .onItem().ifNotNull().transform(user -> Response.ok(user).build());
    }

    @GET
    public Uni<Response> getUsers(@QueryParam("page") int page, @QueryParam("size") int size) {
        // Implementation to be added
        return userService.getUsers(page, size)
                .onItem().ifNotNull().transform(users -> Response.ok(users).build());
    }


    @PUT
    @Path("/{id}")
    public Uni<Response> updateUser(@PathParam("id") UUID id, UpdateUserDTO user) {
        // Implementation to be added
        return userService.updateUser(id, user)
                .onItem().ifNotNull().transform(updatedUser -> Response.ok(updatedUser).build());
    }

    // TODO: Implement DELETE /users/{id} (soft delete a user)
    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteUser(@PathParam("id") String id) {
        // Implementation to be added
        return null;
    }

    // TODO: Implement POST /users/{id}/activate (activate a user)
    @POST
    @Path("/{id}/activate")
    public Uni<Response> activateUser(@PathParam("id") String id) {
        // Implementation to be added
        return null;
    }

    // TODO: Implement POST /users/{id}/deactivate (deactivate a user)
    @POST
    @Path("/{id}/deactivate")
    public Uni<Response> deactivateUser(@PathParam("id") String id) {
        // Implementation to be added
        return null;
    }

    // TODO: Implement POST /users/forgot-password (initiate forgot password process)
    @POST
    @Path("/forgot-password")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // or MediaType.MULTIPART_FORM_DATA if handling file uploads
    public Uni<Response> forgotPassword(@FormParam("email") String email) {
        // Implementation to be added
        return null;
    }

    // TODO: Implement POST /users/reset-password (reset password)
    @POST
    @Path("/reset-password")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // or MediaType.MULTIPART_FORM_DATA if handling file uploads
    public Uni<Response> resetPassword(@FormParam("token") String token, @FormParam("newPassword") String newPassword) {
        // Implementation to be added
        return null;
    }

    // TODO: Implement POST /users/{id}/change-password (change password)
    @POST
    @Path("/{id}/change-password")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // or MediaType.MULTIPART_FORM_DATA if handling file uploads
    public Uni<Response> changePassword(@PathParam("id") String id, @FormParam("oldPassword") String oldPassword, @FormParam("newPassword") String newPassword) {
        // Implementation to be added
        return null;
    }

    // TODO: Implement POST /users/verify-email (verify email address)
    @POST
    @Path("/verify-email")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // or MediaType.MULTIPART_FORM_DATA if handling file uploads
    public Uni<Response> verifyEmail(@FormParam("token") String token) {
        // Implementation to be added
        return null;
    }

    // TODO: Implement POST /users/resend-verification (resend verification email/OTP)
    @POST
    @Path("/resend-verification")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) // or MediaType.MULTIPART_FORM_DATA if handling file uploads
    public Uni<Response> resendVerification(@FormParam("email") String email) {
        // Implementation to be added
        return null;
    }
}