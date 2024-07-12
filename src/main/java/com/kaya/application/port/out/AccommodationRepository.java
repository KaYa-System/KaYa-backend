package com.kaya.application.port.out;

import com.kaya.domain.model.Accommodation;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface AccommodationRepository {
    Uni<Accommodation> findById(UUID id);
    Uni<List<Accommodation>> findAll();
    Uni<Accommodation> save(Accommodation accommodation);
    Uni<Void> delete(UUID id);
}