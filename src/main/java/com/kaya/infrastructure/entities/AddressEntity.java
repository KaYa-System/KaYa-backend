package com.kaya.infrastructure.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address") // Utiliser un nom de table différent pour éviter les conflits
public class AddressEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private Double latitude;
    private Double longitude;
}