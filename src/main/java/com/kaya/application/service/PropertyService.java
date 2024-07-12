package com.kaya.application.service;

import com.kaya.application.dto.PropertySearchCriteriaDTO;
import com.kaya.application.port.in.property.CreatePropertyUseCase;
import com.kaya.application.port.in.property.SearchPropertiesUseCase;
import com.kaya.application.port.out.PropertyRepository;
import com.kaya.domain.model.Property;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PropertyService implements CreatePropertyUseCase, SearchPropertiesUseCase {

    @Inject
    PropertyRepository propertyRepository;

    @Override
    public Uni<Property> createProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Uni<List<Property>> searchProperties(PropertySearchCriteriaDTO criteria) {
        return propertyRepository.search(criteria);
    }

    @Override
    public Uni<Property> getPropertyById(UUID id) {
        return propertyRepository.findById(id);
    }
}

