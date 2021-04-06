package com.account.dto;

import com.account.enums.CompanyStatus;
import com.account.enums.States;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO{

    private Integer id;
    private String title;
    private String address1;
    private String address2;
    private States state;
    private String zip;
    private String representative;
    @Email(message = "Email should be valid")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate establishDate;

    private Boolean enabled;
    private CompanyStatus status;

    @JsonIgnore
    private Boolean deleted;



}
