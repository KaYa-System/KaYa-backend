package com.kaya.application.port.in.user;

import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.AuthMethod;
import io.smallrye.mutiny.Uni;

public interface AuthenticateUserUseCase {
    Uni<String> authenticateWithPin(String phoneNumber, String pinCode);
    Uni<String> authenticateWithExternalProvider(AuthMethod authMethod, String externalToken);
}