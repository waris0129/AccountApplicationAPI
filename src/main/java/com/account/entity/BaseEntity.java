package com.account.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String createdBy;
    private LocalDate createdTime;
    private String updatedBy;
    private LocalDate updatedTime;

    @PrePersist // prePersist to be updated after performing authentication part
    public void onPrePersist(){
        createdBy = "Admin";
        createdTime = LocalDate.now();
        updatedBy = "Admin";
        updatedTime = LocalDate.now();
    }

    @PreUpdate // preUpdate to be updated after performing authentication part
    public void onPreUpdate(){
        updatedBy = "Admin";
        updatedTime = LocalDate.now();
    }


}
