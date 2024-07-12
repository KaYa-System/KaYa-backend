package com.kaya.infrastructure.adapters.out.persistence;

import com.kaya.application.port.out.RentalRepository;
import com.kaya.domain.model.Rental;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RentalRepositoryImpl implements RentalRepository, PanacheRepositoryBase<Rental, UUID> {
    @Override
    public Uni<Rental> findById(UUID id) {
        return null;
    }

    @Override
    public Uni<List<Rental>> findByPropertyId(UUID propertyId) {
        return null;
    }

    @Override
    public Uni<List<Rental>> findByTenantId(UUID tenantId) {
        return null;
    }

    @Override
    public Uni<Rental> save(Rental rental) {
        return null;
    }

    @Override
    public Uni<Void> delete(UUID id) {
        return null;
    }
}
