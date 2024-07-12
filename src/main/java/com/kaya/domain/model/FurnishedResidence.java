package com.kaya.domain.model;

import com.kaya.domain.model.enums.ResidenceType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FurnishedResidence extends Accommodation {
    @Enumerated(EnumType.STRING)
    private ResidenceType type;
}