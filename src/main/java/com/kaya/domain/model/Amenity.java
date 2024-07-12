package com.kaya.domain.model;

import com.kaya.domain.model.enums.AmenityCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amenity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private AmenityCategory category;
}