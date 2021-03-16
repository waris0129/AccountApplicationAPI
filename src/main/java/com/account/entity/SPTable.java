package com.account.entity;


import com.account.enums.RegistrationType;
import com.account.enums.States;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SPTable extends BaseEntity {

    @Column(nullable = false)
    private String companyName;

    private String phone;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company companyId;

    @Enumerated(EnumType.STRING)
    private RegistrationType type;

    private String zipCode;
    private String address;

    @Enumerated(EnumType.STRING)
    private States state;

    private Boolean enabled;
}
