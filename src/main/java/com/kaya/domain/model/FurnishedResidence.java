package com.kaya.domain.model;

import com.kaya.domain.model.enums.ResidenceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FurnishedResidence extends Accommodation {
    private ResidenceType type;
}