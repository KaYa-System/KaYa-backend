package com.kaya.domain.model;

import com.kaya.domain.model.enums.PropertyStatus;
import com.kaya.domain.model.enums.PropertyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private BigDecimal price;

    @ManyToMany
    @JoinTable(name = "property_amenity")
    private List<Amenity> amenities;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    @ElementCollection
    private List<String> images;

    private String virtualTourUrl;

    @ManyToOne
    private User owner;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private Broker broker;
}
