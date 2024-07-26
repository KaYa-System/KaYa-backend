package com.kaya.infrastructure.exception;

import com.kaya.domain.exception.DomainException;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        if (exception instanceof DomainException domainException) {
            return Response.status(getStatusCode(domainException.getErrorCode()))
                    .entity(new ErrorResponse(domainException.getMessage(), domainException.getErrorCode().name()))
                    .build();
        }

        // Handle other types of exceptions (e.g., validation errors)
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(exception.getMessage(), "GENERAL_ERROR"))
                .build();
    }

    private Response.Status getStatusCode(DomainException.ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_INPUT, VALIDATION_ERROR -> Response.Status.BAD_REQUEST;
            case ENTITY_NOT_FOUND -> Response.Status.NOT_FOUND;
            case DUPLICATE_ENTITY -> Response.Status.CONFLICT;
            case UNAUTHORIZED_ACCESS -> Response.Status.UNAUTHORIZED;
            case BUSINESS_RULE_VIOLATION -> Response.Status.PRECONDITION_FAILED;
            default -> Response.Status.INTERNAL_SERVER_ERROR;
        };
    }

    static class ErrorResponse {
        public String message;
        public String errorCode;

        public ErrorResponse(String message, String errorCode) {
            this.message = message;
            this.errorCode = errorCode;
        }
    }
}
