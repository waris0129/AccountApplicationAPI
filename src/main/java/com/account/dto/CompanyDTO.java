package com.account.dto;

import com.account.enums.CompanyStatus;
import com.account.enums.States;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO{

    private String title;
    private String address1;
    private String address2;
    private States state;
    private String zip;
    private String representative;
    private String email;
    private LocalDate establishDate;
    private Boolean enabled;
    private CompanyStatus companyStatus;


}
