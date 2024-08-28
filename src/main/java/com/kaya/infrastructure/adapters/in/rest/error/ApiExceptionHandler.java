
package com.kaya.infrastructure.adapters.in.rest.error;

import com.kaya.domain.exception.ApiException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiExceptionHandler implements ExceptionMapper<ApiException> {
    @Override
    public Response toResponse(ApiException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage(), exception.getErrorCode()))
                .build();
    }

        public record ErrorResponse(String message, String errorCode) {
    }
}
