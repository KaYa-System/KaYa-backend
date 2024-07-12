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
@Description("Represents an individual user in the system")
public class IndividualUser extends User {
    @Description("User's 4-digit PIN for authentication")
    private String pinCode;
}
