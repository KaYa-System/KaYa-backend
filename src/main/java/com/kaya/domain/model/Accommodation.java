package com.kaya.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Accommodation {
    @Id
    @GeneratedValue
    protected UUID id;

    protected String name;

    @OneToOne(cascade = CascadeType.ALL)
    protected Address address;

    @OneToMany(cascade = CascadeType.ALL)
    protected List<Room> rooms;

    @ManyToMany
    @JoinTable(name = "accommodation_amenity")
    protected List<Amenity> amenities;
}