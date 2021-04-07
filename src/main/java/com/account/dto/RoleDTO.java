package com.account.dto;

import com.account.entity.BaseEntity;
import com.account.enums.UserRole;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO{

    private Integer id;
    private UserRole role;


}
