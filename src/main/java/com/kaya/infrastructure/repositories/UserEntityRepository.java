package com.kaya.infrastructure.repositories;

import com.kaya.infrastructure.entities.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UserEntityRepository implements PanacheRepositoryBase<UserEntity, UUID> {
}
