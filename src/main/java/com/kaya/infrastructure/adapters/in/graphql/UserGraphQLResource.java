package com.kaya.infrastructure.adapters.in.graphql;

import com.kaya.application.dto.AbstractCreateUserDTO;
import com.kaya.application.service.UserService;
import com.kaya.domain.exception.EmailAlreadyInUseException;
import com.kaya.domain.exception.PhoneNumberAlreadyInUseException;
import com.kaya.domain.model.User;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.*;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

@GraphQLApi
public class UserGraphQLResource {

    @Inject
    UserService userService;

    @Inject
    Logger logger;

    @Mutation
    @Description("Create a new user")
    public Uni<User> createUser(@Valid @Name("user") AbstractCreateUserDTO createUserDTO) {
        logger.info("Received GraphQL request to create user");
        return userService.createUser(createUserDTO)
                .onFailure().transform(ex -> {
                    logger.error("Failed to create user", ex);
                    if (ex instanceof EmailAlreadyInUseException) {
                        return new GraphQLException("Email already in use");
                    } else if (ex instanceof PhoneNumberAlreadyInUseException) {
                        return new GraphQLException("Phone number already in use");
                    } else {
                        return new GraphQLException("Failed to create user");
                    }
                });
    }
}
