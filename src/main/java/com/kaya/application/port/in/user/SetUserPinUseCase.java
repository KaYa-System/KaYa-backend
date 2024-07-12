package com.kaya.application.port.in.user;

import com.kaya.domain.model.User;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface SetUserPinUseCase {
    Uni<User> setPinCode(UUID userId, String pinCode);
}