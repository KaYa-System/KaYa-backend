package com.kaya.application.dto;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;
import org.eclipse.microprofile.graphql.Type;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Type
@Input
public class TokenResponseDTO {

    @Description("JWT token for authenticated user")
    private String token;

    @Description("Token type, usually 'Bearer'")
    private String tokenType = "Bearer";

    @Description("Token expiration time in seconds")
    private long expiresIn;

    public TokenResponseDTO(String token) {
        this.token = token;
        this.expiresIn = 3600; // Par exemple, 1 heure
    }
}