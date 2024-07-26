package com.kaya.application.dto;

import com.kaya.domain.model.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Data
@EqualsAndHashCode(callSuper = true)
@Input
@Description("DTO for creating an individual user")
public class CreateIndividualUserDTO extends AbstractCreateUserDTO {
    public CreateIndividualUserDTO() {
        this.setType(UserType.INDIVIDUAL);
    }
}
