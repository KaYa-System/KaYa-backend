package com.kaya.application.port.in.property;

import com.kaya.application.dto.PropertySearchCriteriaDTO;
import com.kaya.domain.model.Property;
import com.kaya.domain.model.enums.PropertyType;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface SearchPropertiesUseCase {
    Uni<List<Property>> searchProperties(PropertySearchCriteriaDTO criteria);

    Uni<Property> getPropertyById(UUID id);
}