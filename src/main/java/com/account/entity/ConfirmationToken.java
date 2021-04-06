package com.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted=false")
public class ConfirmationToken extends BaseEntity {

    private String token;

    private Boolean isDeleted;

    @OneToOne(targetEntity = User.class) //unique direction
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(targetEntity = Company.class) //unique direction
    @JoinColumn(name = "company_id")
    private Company company;


    private LocalDate expiredDate;

    public boolean isTokenValid(LocalDate date){

        LocalDate now = LocalDate.now();

        return now.isAfter(date);
    }

    public ConfirmationToken(User user) {
        this.user = user;
        expiredDate = LocalDate.now().plusDays(1);
        token = UUID.randomUUID().toString();
        this.setIsDeleted(false);
    }

    public ConfirmationToken(Company company) {
        this.company = company;
        expiredDate = LocalDate.now().plusDays(1);
        token = UUID.randomUUID().toString();
        this.setIsDeleted(false);
    }



}
