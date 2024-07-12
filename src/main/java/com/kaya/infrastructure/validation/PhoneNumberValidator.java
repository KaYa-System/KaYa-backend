package com.kaya.infrastructure.validation;

import com.kaya.infrastructure.util.PhoneNumberHelper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotBlank handle this
        }
        return PhoneNumberHelper.isValid(value);
    }
}