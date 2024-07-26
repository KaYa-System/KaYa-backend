package com.kaya.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Broker {

    private UUID id;

    private String name;
    private String email;
    private String phoneNumber;
    private String licenseNumber;

    private List<Property> managedProperties;
}