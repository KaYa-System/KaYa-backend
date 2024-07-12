package com.kaya.infrastructure.adapters.in.graphql;

import com.kaya.application.port.in.property.*;
import com.kaya.application.dto.PropertySearchCriteriaDTO;
import com.kaya.domain.model.Property;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;

import java.util.List;
import java.util.UUID;

@GraphQLApi
public class PropertyGraphQLResource {
    @Inject
    CreatePropertyUseCase createPropertyUseCase;
    @Inject
    SearchPropertiesUseCase searchPropertiesUseCase;

    @Mutation
    public Uni<Property> createProperty(Property property) {
        return createPropertyUseCase.createProperty(property);
    }

    @Query
    @Description("Search for properties based on given criteria")
    public Uni<List<Property>> searchProperties(PropertySearchCriteriaDTO criteria) {
        return searchPropertiesUseCase.searchProperties(criteria);
    }

    @Query
    public Uni<Property> getProperty(@Name("id") UUID id) {
        return searchPropertiesUseCase.getPropertyById(id);
    }
}
