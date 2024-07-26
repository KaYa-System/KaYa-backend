package com.kaya.infrastructure.entities;

import com.kaya.domain.model.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accommodation") // Utiliser un nom de table différent pour éviter les conflits
public abstract class AccommodationEntity {
    @Id
    @GeneratedValue
    protected UUID id;

    protected String name;

    @OneToOne(cascade = CascadeType.ALL)
    protected AddressEntity address;

    @OneToMany(cascade = CascadeType.ALL)
    protected List<RoomEntity> rooms;

    @ManyToMany
    @JoinTable(name = "accommodation_amenity")
    protected List<AmenityEntity> amenities;
}