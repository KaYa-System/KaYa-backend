package com.kaya.domain.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.graphql.Description;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Description("Represents a corporate user in the system")
public class CorporateUser extends User {
    @Description("Name of the company")
    private String companyName;

    @Description("Company's registration number")
    private String companyRegistrationNumber;

    @Description("User's job title within the company")
    private String jobTitle;
}
