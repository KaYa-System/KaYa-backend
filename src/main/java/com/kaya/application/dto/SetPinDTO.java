package com.kaya.application.dto;

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
public class SetPinDTO {

    @NotBlank(message = "PIN is required")
    @Pattern(regexp = "^\\d{4}$", message = "PIN must be a 4-digit number")
    @Description("The PIN code to set")
    private String pinCode;
}