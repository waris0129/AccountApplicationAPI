package com.account.entity;


import com.account.enums.RegistrationType;
import com.account.enums.States;
import com.account.enums.VendorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted=false")
public class Vendor extends BaseEntity {

    @Column(nullable = false)
    private String companyName;

    private String phone;

    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    private RegistrationType type;

    private String zipCode;
    private String address;

    @Enumerated(EnumType.STRING)
    private States state;

    @Enumerated(EnumType.STRING)
    private VendorStatus status;

    private Boolean enabled;

    private Boolean deleted;
}
