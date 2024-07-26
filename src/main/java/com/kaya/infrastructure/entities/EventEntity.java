package com.kaya.infrastructure.entities;

import com.kaya.domain.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class EventEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    private PropertyEntity venue;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int capacity;
    private BigDecimal price;

    @ManyToOne
    private UserEntity organizer;

    @Enumerated(EnumType.STRING)
    private EventStatus status;
}