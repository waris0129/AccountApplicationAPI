package com.account.entity;

import com.account.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Boolean enabled;

}
