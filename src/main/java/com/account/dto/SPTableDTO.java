package com.account.dto;


import com.account.entity.BaseEntity;
import com.account.entity.Company;
import com.account.enums.RegistrationType;
import com.account.enums.States;
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
public class SPTableDTO{

    private String companyName;
    private String phone;
    private String email;
    private Company companyId;
    private RegistrationType type;
    private String zipCode;
    private String address;
    private States state;
    private Boolean enabled;
}
