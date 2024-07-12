package com.kaya.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyEvaluation {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Property property;

    @ManyToOne
    private Broker evaluator;

    private BigDecimal estimatedValue;
    private LocalDate evaluationDate;

    @Column(length = 1000)
    private String comments;
}