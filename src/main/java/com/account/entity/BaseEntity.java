package com.account.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,updatable = false)
    private String createdBy;
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdTime;
    @Column(nullable = false)
    private String updatedBy;
    @Column(nullable = false)
    private LocalDateTime updatedTime;

    @PrePersist // prePersist to be updated after performing authentication part
    public void onPrePersist(){

        createdBy = "Root";
        createdTime = LocalDateTime.now();
        updatedBy = "Root";
        updatedTime = LocalDateTime.now();
    }

    @PreUpdate // preUpdate to be updated after performing authentication part
    public void onPreUpdate(){
        updatedBy = "Root";;
        updatedTime = LocalDateTime.now();
    }


}
