package com.kaya.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Accommodation {

    protected UUID id;

    protected String name;

    protected Address address;

    protected List<Room> rooms;

    protected List<Amenity> amenities;
}