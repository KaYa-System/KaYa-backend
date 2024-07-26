package com.kaya.infrastructure.entities;

import com.kaya.domain.model.enums.PropertyStatus;
import com.kaya.domain.model.enums.PropertyType;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a property in the system")
@Table(name = "property") // Utiliser un nom de table différent pour éviter les conflits
public class PropertyEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Schema(description = "Unique identifier of the property")
    private UUID id;

    @Schema(description = "Title of the property", example = "Beautiful Seaside Villa")
    private String title;

    @Column(length = 1000)
    @Schema(description = "Detailed description of the property")
    private String description;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Type of the property", example = "VILLA")
    private PropertyType type;

    @OneToOne(cascade = CascadeType.ALL)
    @Schema(description = "Address of the property")
    private AddressEntity address;

    @Schema(description = "Price of the property", example = "500000.00")
    private BigDecimal price;

    @ManyToMany
    @JoinTable(name = "property_amenity")
    @Schema(description = "List of amenities available in the property")
    private List<AmenityEntity> amenities;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the property", example = "AVAILABLE")
    private PropertyStatus status;

    @ElementCollection
    @Schema(description = "List of image URLs for the property")
    private List<String> images;

    @Schema(description = "URL for virtual tour of the property", example = "https://example.com/virtual-tour")
    private String virtualTourUrl;

    @ManyToOne
    @Schema(description = "Owner of the property")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    @Schema(description = "Broker associated with the property")
    private BrokerEntity broker;
}