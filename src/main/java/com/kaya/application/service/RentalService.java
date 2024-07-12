package com.kaya.application.service;

import com.kaya.application.port.in.rental.CreateRentalUseCase;
import com.kaya.application.port.out.RentalRepository;
import com.kaya.domain.model.Rental;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RentalService implements CreateRentalUseCase {

    @Inject
    RentalRepository rentalRepository;


    @Override
    public Uni<Rental> createRental(Rental rental) {
        return rentalRepository.save(rental);
    }
}
