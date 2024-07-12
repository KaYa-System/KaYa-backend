package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.port.in.property.*;
import com.kaya.application.dto.PropertySearchCriteriaDTO;
import com.kaya.domain.model.Property;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.UUID;

@Path("/properties")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PropertyResource {
    @Inject
    CreatePropertyUseCase createPropertyUseCase;
    @Inject
    SearchPropertiesUseCase searchPropertiesUseCase;

    @POST
    public Uni<Response> createProperty(Property property) {
        return createPropertyUseCase.createProperty(property)
                .onItem().transform(createdProperty -> Response.status(Response.Status.CREATED).entity(createdProperty).build());
    }

    @GET
    public Uni<Response> searchProperties(@BeanParam PropertySearchCriteriaDTO criteria) {
        return searchPropertiesUseCase.searchProperties(criteria)
                .onItem().transform(properties -> Response.ok(properties).build());
    }


    @GET
    @Path("/{id}")
    public Uni<Response> getProperty(@PathParam("id") UUID id) {
        return searchPropertiesUseCase.getPropertyById(id)
                .onItem().transform(property -> property != null ? Response.ok(property).build() : Response.status(Response.Status.NOT_FOUND).build());
    }
}