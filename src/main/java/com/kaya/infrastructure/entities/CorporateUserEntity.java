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
@Description("Represents a corporate user in the system")
@Table(name = "corporateuser") // Utiliser un nom de table différent pour éviter les conflits
public class CorporateUserEntity extends UserEntity {
    @Description("Name of the company")
    private String companyName;

    @Description("Company's registration number")
    private String companyRegistrationNumber;

    @Description("User's job title within the company")
    private String jobTitle;
}
