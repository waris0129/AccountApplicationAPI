package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "enabled=true")
public class Category extends BaseEntity{

    private String category;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Boolean enabled;

}
