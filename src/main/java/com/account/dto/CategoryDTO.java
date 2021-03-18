package com.account.dto;

import com.account.entity.Company;
import com.account.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO{

    private Integer id;
    private ProductCategory category;
    private Company company;
    private Boolean enabled;

}
