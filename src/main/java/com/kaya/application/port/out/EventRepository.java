package com.kaya.application.port.out;

import com.kaya.domain.model.Event;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface EventRepository {
    Uni<Event> findById(UUID id);
    Uni<List<Event>> findByVenueId(UUID venueId);
    Uni<List<Event>> findByOrganizerId(UUID organizerId);
    Uni<Event> save(Event event);
    Uni<Void> delete(UUID id);
}