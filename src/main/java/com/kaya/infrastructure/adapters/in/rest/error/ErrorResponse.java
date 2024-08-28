package com.kaya.infrastructure.adapters.in.rest.error;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private String title;
    private int status;
    private List<ValidationError> violations;

    public ErrorResponse(String title, int status, List<ValidationError> violations) {
        this.title = title;
        this.status = status;
        this.violations = violations;
    }

}