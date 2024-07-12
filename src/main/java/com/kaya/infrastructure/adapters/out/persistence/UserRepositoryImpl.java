package com.kaya.infrastructure.adapters.out.persistence;

import com.kaya.application.port.out.UserRepository;
import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.AuthMethod;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository, PanacheRepositoryBase<User, UUID> {

    @Override
    public Uni<User> findById(UUID id) {
        return PanacheRepositoryBase.super.findById(id);
    }

    @Override
    public Uni<User> findByEmail(String email) {
        return find("email", email).firstResult();
    }

    @Override
    public Uni<User> findByPhoneNumber(String phoneNumber) {
        return find("phoneNumber", phoneNumber).firstResult();
    }

    @Override
    public Uni<User> findByExternalId(AuthMethod authMethod, String externalId) {
        return find("authMethod = ?1 and externalId = ?2", authMethod, externalId).firstResult();
    }

    @Override
    public Uni<User> save(User user) {
        if (user.getId() == null) {
            return persist(user).map(v -> user);
        } else {
            return getSession()
                    .flatMap(session -> session.merge(user))
                    .flatMap(mergedUser -> persistAndFlush(mergedUser).map(v -> mergedUser));
        }
    }

    @Override
    public Uni<Void> delete(UUID id) {
        return deleteById(id)
                .onItem().transformToUni(deleted -> deleted ? Uni.createFrom().voidItem() : Uni.createFrom().failure(new IllegalArgumentException("User not found")));
    }
}