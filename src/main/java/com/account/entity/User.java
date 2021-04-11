package com.account.entity;

import com.account.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted=false")
public class User extends BaseEntity{

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Boolean enabled;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role role;

    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    private UserStatus status;


}
