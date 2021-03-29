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
    private String inventoryNo;
    private ProductNameDTO name;
    private Integer qty;
    private Integer availableStock;
    private Integer price;
    private CategoryDTO category;
    private CompanyDTO company;
    private Boolean enabled;
}
