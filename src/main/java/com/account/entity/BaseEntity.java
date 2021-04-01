package com.account.entity;


import com.account.controller.LoginController;
import com.account.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(LoginController.class)
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        createdBy = role;
        createdTime = LocalDateTime.now();
        updatedBy = role;
        updatedTime = LocalDateTime.now();
    }

    @PreUpdate // preUpdate to be updated after performing authentication part
    public void onPreUpdate(){

        if (this.getUpdatedBy().endsWith("from ConfirmationToken")){
            String role = this.getUpdatedBy().replace(" from ConfirmationToken","");
            this.setUpdatedBy(role);
            return;
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        updatedBy = role;
        updatedTime = LocalDateTime.now();
    }


}
