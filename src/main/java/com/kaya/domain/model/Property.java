package com.kaya.domain.model;

import com.kaya.domain.model.enums.PropertyStatus;
import com.kaya.domain.model.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Property {

    private UUID id;

    private String title;

    private String description;

    private PropertyType type;

    private Address address;

    private BigDecimal price;

    private List<Amenity> amenities;

    private PropertyStatus status;

    private List<String> images;

    private String virtualTourUrl;

    private User owner;

    private Broker broker;
}