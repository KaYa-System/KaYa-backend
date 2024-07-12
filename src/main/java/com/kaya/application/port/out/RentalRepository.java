package com.kaya.application.port.out;

import com.kaya.domain.model.Rental;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface RentalRepository {
    Uni<Rental> findById(UUID id);
    Uni<List<Rental>> findByPropertyId(UUID propertyId);
    Uni<List<Rental>> findByTenantId(UUID tenantId);
    Uni<Rental> save(Rental rental);
    Uni<Void> delete(UUID id);
}