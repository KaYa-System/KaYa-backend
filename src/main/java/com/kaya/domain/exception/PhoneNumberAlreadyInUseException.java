package com.kaya.domain.exception;

public class PhoneNumberAlreadyInUseException extends DomainException {
    public PhoneNumberAlreadyInUseException(String message) {
        super(message, ErrorCode.DUPLICATE_ENTITY);
    }
}
