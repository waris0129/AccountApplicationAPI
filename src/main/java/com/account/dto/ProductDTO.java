package com.account.dto;

import com.account.entity.Category;
import com.account.entity.Company;
import com.account.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO{

    private Integer id;
    private String name;
    private String description;
    private Integer qty;
    private Integer price;
    private Category categoryId;
    private Unit unit;
    private Integer lowLimitAlert;
    private Integer tax;
    private Company company;
    private Boolean enabled;
}
