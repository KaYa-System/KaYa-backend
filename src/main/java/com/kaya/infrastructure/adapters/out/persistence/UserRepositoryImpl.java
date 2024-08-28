package com.kaya.infrastructure.adapters.out.persistence;

import com.kaya.application.port.out.UserRepository;
import com.kaya.domain.exception.DomainException;
import com.kaya.domain.model.User;
import com.kaya.domain.model.IndividualUser;
import com.kaya.domain.model.CorporateUser;
import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.domain.model.enums.UserType;
import com.kaya.infrastructure.entities.CorporateUserEntity;
import com.kaya.infrastructure.entities.IndividualUserEntity;
import com.kaya.infrastructure.entities.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Uni;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.enterprise.context.ApplicationScoped;
import org.modelmapper.ModelMapper;

import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Tag(name = "User Repository", description = "User data persistence operations")
public class UserRepositoryImpl implements UserRepository {

    @Inject
    ModelMapper modelMapper;

    @Inject
    com.kaya.infrastructure.repositories.UserEntityRepository userEntityRepository;

    @Override
    public Uni<User> findById(UUID id) {
        return userEntityRepository.findById(id)
                .map(this::mapToDomainUser);
    }

    @Override
    public Uni<User> findByEmail(String email) {
        return userEntityRepository.find("email", email).firstResult()
                .map(this::mapToDomainUser);
    }


    @Override
    public Uni<List<User>> findPage(int page, int size) {
        return userEntityRepository.findAll().page(Page.of(page, size)).list()
                .map(userEntities -> userEntities.stream()
                        .map(this::mapToDomainUser)
                        .toList());
    }


    @Override
    public Uni<User> findByPhoneNumber(String phoneNumber) {
        return userEntityRepository.find("phoneNumber", phoneNumber).firstResult()
                .map(this::mapToDomainUser);
    }

    @Override
    public Uni<User> findByExternalId(AuthMethod authMethod, String externalId) {
        return userEntityRepository.find("authMethod = ?1 and externalId = ?2", authMethod, externalId).firstResult()
                .map(this::mapToDomainUser);
    }


    @WithSession
    @Override
    public Uni<User> save(User user) {
        UserEntity userEntity = mapToUserEntity(user);
        if (userEntity.getId() == null) {
            return userEntityRepository.persistAndFlush(userEntity)
                    .map(this::mapToDomainUser);
        } else {
            return userEntityRepository.getSession()
                    .flatMap(session -> session.merge(userEntity))
                    .flatMap(mergedEntity -> userEntityRepository.persistAndFlush(mergedEntity)
                            .map(this::mapToDomainUser));
        }
    }

    private UserEntity mapToUserEntity(User user) {
        UserEntity entity;
        if (user instanceof CorporateUser) {
             entity = modelMapper.map(user, CorporateUserEntity.class);
        } else if (user instanceof IndividualUser) {
             entity = modelMapper.map(user, IndividualUserEntity.class);
        } else {
            throw new DomainException("User is null", DomainException.ErrorCode.INVALID_INPUT);
        }

        return entity;
    }


    @Override
    public Uni<Void> delete(UUID id) {
        return userEntityRepository.deleteById(id)
                .onItem().transformToUni(deleted -> deleted ? Uni.createFrom().voidItem() : Uni.createFrom().failure(new IllegalArgumentException("User not found")));
    }

    private User mapToDomainUser(UserEntity entity) {
        if (entity == null) {
            throw new DomainException("User not found", DomainException.ErrorCode.ENTITY_NOT_FOUND);
        }

        if (entity.getType() == UserType.CORPORATE) {
            return modelMapper.map(entity, CorporateUser.class);
        } else {
            return modelMapper.map(entity, IndividualUser.class);
        }
    }

}