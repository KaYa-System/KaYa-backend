package com.kaya.application.dto;

import com.kaya.domain.model.enums.AuthMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Input
public class LoginExternalDTO {

    @NotNull(message = "Auth method is required")
    @Description("External authentication method")
    private AuthMethod authMethod;

    @NotBlank(message = "External token is required")
    @Description("Token from external authentication provider")
    private String externalToken;
}