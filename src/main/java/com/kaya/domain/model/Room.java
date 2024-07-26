package com.kaya.domain.model;

import com.kaya.domain.model.enums.RoomType;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Room {

    protected UUID id;

    private String name;

    private RoomType type;

    private int capacity;
    private double area;

    private List<Amenity> amenities;
}