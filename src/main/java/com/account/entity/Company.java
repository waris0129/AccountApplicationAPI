package com.account.entity;

import com.account.enums.CompanyStatus;
import com.account.enums.States;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Where(clause = "deleted=false")
@Component
public class Company extends BaseEntity{

    @Column(nullable = false,updatable = false)
    private String title;

    @Column(nullable = false)
    private String address1;

    private String address2;

    @Enumerated(EnumType.STRING)
    private States state;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String representative;

    @Email(message = "Email should be valid")
    @Column(nullable = false,updatable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate establishDate;
    private Boolean enabled;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status;

    private Boolean deleted;

}
