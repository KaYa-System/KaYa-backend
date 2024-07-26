package com.kaya.infrastructure.entities;

import com.kaya.domain.model.enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue
    protected UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private int capacity;
    private double area;

    @ManyToMany
    @JoinTable(
            name = "room_amenity",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<AmenityEntity> amenities;
}