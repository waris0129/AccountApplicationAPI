package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{

    private String name;
    private String description;
    private Integer qty;
    private Integer price;
    @OneToOne
    private Category categoryId;
    private String unit;
    private Integer lowLimitAlert;
    private Integer tax;
    @OneToOne
    private Company companyId;
    private Boolean enabled;
}
