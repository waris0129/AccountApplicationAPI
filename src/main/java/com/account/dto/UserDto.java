package com.account.dto;

import com.account.entity.BaseEntity;
import com.account.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    @JsonIgnore
    private Boolean enabled;
    private String phone;
    private CompanyDTO company;
    private RoleDTO role;

    @JsonIgnore
    private Boolean deleted;


}
