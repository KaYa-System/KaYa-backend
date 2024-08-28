package com.kaya.infrastructure.adapters.in.rest.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {
    private String field;
    private String message;

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

}