package com.kaya.application.dto;

import com.kaya.infrastructure.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Input
public class LoginPinDTO {

    @NotBlank(message = "Phone number is required")
    @PhoneNumber(message = "Invalid phone number")
    @Description("User's phone number")
    private String phoneNumber;

    @NotBlank(message = "PIN is required")
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
    @Description("User's 4-digit PIN")
    private String pinCode;
}