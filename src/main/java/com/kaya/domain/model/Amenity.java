package com.kaya.domain.model;

import com.kaya.domain.model.enums.AmenityCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Amenity {
    private Long id;

    private String name;
    private String description;

    private AmenityCategory category;
}