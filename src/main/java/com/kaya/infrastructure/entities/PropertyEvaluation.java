package com.kaya.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "propertyevaluation")
public class PropertyEvaluation {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private PropertyEntity property;

    @ManyToOne
    private BrokerEntity evaluator;

    private BigDecimal estimatedValue;
    private LocalDate evaluationDate;

    @Column(length = 1000)
    private String comments;
}