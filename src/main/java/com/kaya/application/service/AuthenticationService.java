package com.kaya.application.service;

import com.kaya.application.port.in.user.AuthenticateUserUseCase;
import com.kaya.application.port.out.UserRepository;
import com.kaya.domain.exception.DomainException;
import com.kaya.domain.model.IndividualUser;
import com.kaya.domain.model.User;
import com.kaya.domain.model.enums.AuthMethod;
import com.kaya.infrastructure.external.ExternalAuthService;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AuthenticationService implements AuthenticateUserUseCase {

    @Inject
    UserRepository userRepository;

    @Inject
    ExternalAuthService externalAuthService;

    @Inject
    Logger logger;

    @Override
    public Uni<String> authenticateWithPin(String phoneNumber, String pinCode) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .onItem().transform(Unchecked.function(user -> {
                    if (user instanceof IndividualUser && ((IndividualUser) user).getPinCode().equals(pinCode)) {
                        return generateJwtToken(user);
                    } else {
                        throw new DomainException("Invalid credentials", DomainException.ErrorCode.UNAUTHORIZED_ACCESS);
                    }
                }));
    }

    @Override
    public Uni<String> authenticateWithExternalProvider(AuthMethod authMethod, String externalToken) {
        return externalAuthService.validateToken(authMethod, externalToken)
                .onItem().transformToUni(externalId -> userRepository.findByExternalId(authMethod, externalId))
                .onItem().transform(this::generateJwtToken)
                .onFailure().transform(e -> new DomainException("Authentication failed", DomainException.ErrorCode.UNAUTHORIZED_ACCESS));
    }

    private String generateJwtToken(User user) {
        // Implement JWT token generation logic here
        return "generated.jwt.token";
    }
}
