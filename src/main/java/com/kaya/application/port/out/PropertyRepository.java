package com.kaya.application.port.out;

import com.kaya.domain.model.Property;
import com.kaya.application.dto.PropertySearchCriteriaDTO;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository {
    Uni<Property> findById(UUID id);
    Uni<List<Property>> search(PropertySearchCriteriaDTO criteria);
    Uni<Property> save(Property property);
    Uni<Void> delete(UUID id);
}