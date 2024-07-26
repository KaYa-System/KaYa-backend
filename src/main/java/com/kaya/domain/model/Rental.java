package com.kaya.domain.model;

import com.kaya.domain.model.enums.RentalStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Rental {

    private UUID id;

    private Property property;

    private User tenant;

    private User landlord;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal monthlyRent;

    private RentalStatus status;
}