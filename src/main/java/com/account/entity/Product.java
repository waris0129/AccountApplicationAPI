package com.account.entity;

import com.account.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private Integer lowLimitAlert;
    private Integer tax;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Boolean enabled;
}
