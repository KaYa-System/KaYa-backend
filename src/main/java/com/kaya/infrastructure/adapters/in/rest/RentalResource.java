package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.port.in.rental.CreateRentalUseCase;
import com.kaya.domain.model.Rental;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

@Path("/rentals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RentalResource {
    @Inject
    CreateRentalUseCase createRentalUseCase;

    @POST
    public Uni<Response> createRental(Rental rental) {
        return createRentalUseCase.createRental(rental)
                .onItem().transform(createdRental -> Response.status(Response.Status.CREATED).entity(createdRental).build())
                .onFailure().recoverWithItem(throwable -> Response.status(Response.Status.BAD_REQUEST).build());
    }
}