package com.kaya.application.port.in.rental;

import com.kaya.domain.model.Rental;
import io.smallrye.mutiny.Uni;

public interface CreateRentalUseCase {
    Uni<Rental> createRental(Rental rental);
}