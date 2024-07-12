package com.kaya.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Broker {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private String phoneNumber;
    private String licenseNumber;

    @OneToMany(mappedBy = "broker")
    private List<Property> managedProperties;
}