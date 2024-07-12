package com.kaya.domain.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final ErrorCode errorCode;

    public DomainException(String message) {
        super(message);
        this.errorCode = ErrorCode.GENERAL_ERROR;
    }

    public DomainException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.GENERAL_ERROR;
    }

    public DomainException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public enum ErrorCode {
        GENERAL_ERROR,
        INVALID_INPUT,
        ENTITY_NOT_FOUND,
        DUPLICATE_ENTITY,
        UNAUTHORIZED_ACCESS,
        VALIDATION_ERROR,
        BUSINESS_RULE_VIOLATION
        // Ajoutez d'autres codes d'erreur spécifiques à votre domaine si nécessaire
    }
}