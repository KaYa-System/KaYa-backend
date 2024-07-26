package com.kaya.infrastructure.entities;


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
@Table(name = "broker")
public class BrokerEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private String phoneNumber;
    private String licenseNumber;

    @OneToMany(mappedBy = "broker")
    private List<PropertyEntity> managedProperties;
}