package com.kaya.infrastructure.entities;

import com.kaya.domain.model.enums.ResidenceType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "furnishedresidence")
public class FurnishedResidenceEntity extends AccommodationEntity {
    @Enumerated(EnumType.STRING)
    private ResidenceType type;
}