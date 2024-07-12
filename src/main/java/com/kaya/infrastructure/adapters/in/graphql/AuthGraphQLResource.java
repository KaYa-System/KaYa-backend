package com.kaya.infrastructure.adapters.in.graphql;

import com.kaya.application.dto.LoginExternalDTO;
import com.kaya.application.dto.LoginPinDTO;
import com.kaya.application.dto.TokenResponseDTO;
import com.kaya.application.port.in.user.AuthenticateUserUseCase;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;

@GraphQLApi
public class AuthGraphQLResource {

    @Inject
    AuthenticateUserUseCase authenticateUserUseCase;

    @Mutation
    @Description("Authenticate user with phone number and PIN")
    public Uni<TokenResponseDTO> loginWithPin(@Name("loginPin") LoginPinDTO loginPinDTO) {
        return authenticateUserUseCase.authenticateWithPin(loginPinDTO.getPhoneNumber(), loginPinDTO.getPinCode())
                .map(TokenResponseDTO::new);
    }

    @Mutation
    @Description("Authenticate user with external provider")
    public Uni<TokenResponseDTO> loginWithExternalProvider(@Name("loginExternal") LoginExternalDTO loginExternalDTO) {
        return authenticateUserUseCase.authenticateWithExternalProvider(loginExternalDTO.getAuthMethod(), loginExternalDTO.getExternalToken())
                .map(TokenResponseDTO::new);
    }
}