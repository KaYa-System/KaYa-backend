package com.kaya.application.port.out;

import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.AuthMethod;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface UserRepository {
    Uni<User> findById(UUID id);
    Uni<User> findByEmail(String email);
    Uni<User> findByPhoneNumber(String phoneNumber);
    Uni<User> findByExternalId(AuthMethod authMethod, String externalId);
    Uni<User> save(User user);
    Uni<Void> delete(UUID id);
}
