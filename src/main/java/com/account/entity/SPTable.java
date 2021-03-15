package com.account.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SPTable extends BaseEntity {

    private String companyName;
    private String phone;
    private String email;
    @OneToOne
    private Company company;
    private String type;
    private String zipCode;
    private String address;
    private String state;
    private Boolean enabled;
}
