package com.kaya.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CorporateUser extends User {
    private String companyName;

    private String companyRegistrationNumber;

    private String jobTitle;
}
