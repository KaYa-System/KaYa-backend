package com.kaya.domain.exception;


public class EmailAlreadyInUseException extends DomainException {
    public EmailAlreadyInUseException(String message) {
        super(message, ErrorCode.DUPLICATE_ENTITY);
    }
}