package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity{

    private String title;
    private String address1;
    private String address2;
    private String state;
    private String zip;
    private String representative;
    private String email;
    private LocalDate establishDate;
    private Boolean enabled;


}
