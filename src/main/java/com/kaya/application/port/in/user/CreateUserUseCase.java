package com.kaya.application.port.in.user;

import com.kaya.application.dto.AbstractCreateUserDTO;
import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.UserType;
import com.kaya.domain.model.enums.AuthMethod;
import io.smallrye.mutiny.Uni;

public interface CreateUserUseCase {
    Uni<User> createUser(AbstractCreateUserDTO dto);
}
