package com.kaya.application.port.in.user;

import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.UserType;
import com.kaya.domain.model.enums.AuthMethod;
import io.smallrye.mutiny.Uni;

public interface CreateUserUseCase {
    Uni<User> createIndividualUser(String phoneNumber, UserType type);
    Uni<User> createIndividualUserWithExternalAuth(String email, UserType type, AuthMethod authMethod, String externalId);
    Uni<User> createCorporateUser(String email, UserType type, String companyName, String registrationNumber);
}