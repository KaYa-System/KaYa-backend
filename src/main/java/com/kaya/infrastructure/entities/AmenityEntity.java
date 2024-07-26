package com.kaya.infrastructure.entities;

import com.kaya.domain.model.enums.AmenityCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "amenity") // Utiliser un nom de table différent pour éviter les conflits
public class AmenityEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private AmenityCategory category;
}