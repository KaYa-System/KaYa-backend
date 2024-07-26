package com.kaya.infrastructure.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.graphql.Description;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Description("Represents an individual user in the system")
@Table(name = "individualuser") // Utiliser un nom de table différent pour éviter les conflits
public class IndividualUserEntity extends UserEntity {
    @Description("User's 4-digit PIN for authentication")
    private String pinCode;
}