package com.kaya.domain.model;

import com.kaya.domain.model.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    private Property venue;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int capacity;
    private BigDecimal price;

    @ManyToOne
    private User organizer;

    @Enumerated(EnumType.STRING)
    private EventStatus status;
}