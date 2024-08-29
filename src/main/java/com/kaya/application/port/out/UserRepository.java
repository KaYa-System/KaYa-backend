package com.kaya.application.port.out;

import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.AuthMethod;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    Uni<User> findById(UUID id);
    Uni<User> findByEmail(String email);
    Uni<List<User>> findPage(int page, int size);
    Uni<User> findByPhoneNumber(String phoneNumber);
    Uni<User> findByExternalId(AuthMethod authMethod, String externalId);
    Uni<User> save(User user);
    Uni<Void> delete(UUID id);

    Uni<User> update(UUID userId, User user);
}
