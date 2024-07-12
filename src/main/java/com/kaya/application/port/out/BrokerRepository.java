package com.kaya.application.port.out;

import com.kaya.domain.model.Broker;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface BrokerRepository {
    Uni<Broker> findById(UUID id);
    Uni<List<Broker>> findAll();
    Uni<Broker> save(Broker broker);
    Uni<Void> delete(UUID id);
}