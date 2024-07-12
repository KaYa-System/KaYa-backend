package com.kaya.application.port.in.property;

import com.kaya.domain.model.Property;
import io.smallrye.mutiny.Uni;

public interface CreatePropertyUseCase {
    Uni<Property> createProperty(Property property);
}