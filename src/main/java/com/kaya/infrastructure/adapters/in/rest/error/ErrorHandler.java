package com.kaya.infrastructure.adapters.in.rest.error;

import com.kaya.domain.exception.EmailAlreadyInUseException;
import com.kaya.domain.exception.PhoneNumberAlreadyInUseException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ErrorHandler {

    public ErrorResponse handleException(Throwable throwable) {
        if (throwable instanceof EmailAlreadyInUseException) {
            return createErrorResponse("Email already in use", Response.Status.BAD_REQUEST, "email");
        } else if (throwable instanceof PhoneNumberAlreadyInUseException) {
            return createErrorResponse("Phone number already in use", Response.Status.BAD_REQUEST, "phoneNumber");
        } else if (throwable instanceof ConstraintViolationException) {
            return handleConstraintViolation((ConstraintViolationException) throwable);
        } else {
            return createErrorResponse(throwable.getMessage(), Response.Status.INTERNAL_SERVER_ERROR, null);
        }
    }

    private ErrorResponse handleConstraintViolation(ConstraintViolationException e) {
        List<ValidationError> violations = e.getConstraintViolations().stream()
                .map(this::createValidationError)
                .collect(Collectors.toList());

        return new ErrorResponse("Constraint Violation", Response.Status.BAD_REQUEST.getStatusCode(), violations);
    }

    private ValidationError createValidationError(ConstraintViolation<?> violation) {
        return new ValidationError(getFieldName(violation), violation.getMessage());
    }

    private String getFieldName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }

    private ErrorResponse createErrorResponse(String message, Response.Status status, String field) {
        List<ValidationError> violations = field != null
                ? List.of(new ValidationError(field, message))
                : List.of();
        return new ErrorResponse(message, status.getStatusCode(), violations);
    }
}
