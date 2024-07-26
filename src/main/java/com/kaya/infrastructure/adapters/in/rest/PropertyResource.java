package com.kaya.infrastructure.adapters.in.rest;

import com.kaya.application.port.in.property.*;
import com.kaya.application.dto.PropertySearchCriteriaDTO;
import com.kaya.domain.model.Property;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Path("/properties")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Property", description = "Property management operations")
public class PropertyResource {
    @Inject
    CreatePropertyUseCase createPropertyUseCase;
    @Inject
    SearchPropertiesUseCase searchPropertiesUseCase;

    @POST
    @Operation(summary = "Create a new property", description = "Creates a new property with the given details")
    @APIResponse(responseCode = "201", description = "Property created successfully")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Uni<Response> createProperty(
            @Parameter(description = "Property to be created", required = true) Property property) {
        return createPropertyUseCase.createProperty(property)
                .onItem().transform(createdProperty -> Response.status(Response.Status.CREATED).entity(createdProperty).build());
    }

    @GET
    @Operation(summary = "Search properties", description = "Search for properties based on given criteria")
    @APIResponse(responseCode = "200", description = "Successful search operation")
    public Uni<Response> searchProperties(@BeanParam PropertySearchCriteriaDTO criteria) {
        return searchPropertiesUseCase.searchProperties(criteria)
                .onItem().transform(properties -> Response.ok(properties).build());
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a property by ID", description = "Retrieves a property based on its ID")
    @APIResponse(responseCode = "200", description = "Property found")
    @APIResponse(responseCode = "404", description = "Property not found")
    public Uni<Response> getProperty(
            @Parameter(description = "ID of the property to be retrieved", required = true)
            @PathParam("id") UUID id) {
        return searchPropertiesUseCase.getPropertyById(id)
                .onItem().transform(property -> property != null ? Response.ok(property).build() : Response.status(Response.Status.NOT_FOUND).build());
    }
}