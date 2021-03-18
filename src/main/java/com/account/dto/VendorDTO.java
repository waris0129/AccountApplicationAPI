package com.account.dto;


import com.account.entity.BaseEntity;
import com.account.entity.Company;
import com.account.enums.RegistrationType;
import com.account.enums.States;
import com.account.enums.VendorStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    private Integer id;
    private String companyName;
    private String phone;
    private String email;
    private CompanyDTO company;
    private RegistrationType type;
    private String zipCode;
    private String address;
    private States state;
    private VendorStatus status;
    private Boolean enabled;
    @JsonIgnore
    private Boolean deleted;
}
