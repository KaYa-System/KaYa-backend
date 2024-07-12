package com.kaya.application.dto;

import com.kaya.infrastructure.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Input
public class VerifyPhoneDTO {

    @NotBlank(message = "Phone number is required")
    @PhoneNumber(message = "Invalid phone number")
    @Description("Phone number")
    private String phoneNumber;

    @NotBlank(message = "OTP is required")
    @Pattern(regexp = "^\\d{6}$", message = "OTP must be a 6-digit number")
    @Description("OTP")
    private String otp;
}