package com.kaya.application.port.out;

import com.kaya.domain.model.PropertyEvaluation;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface PropertyEvaluationRepository {
    Uni<PropertyEvaluation> findById(UUID id);
    Uni<List<PropertyEvaluation>> findByPropertyId(UUID propertyId);
    Uni<PropertyEvaluation> save(PropertyEvaluation evaluation);
    Uni<Void> delete(UUID id);
}