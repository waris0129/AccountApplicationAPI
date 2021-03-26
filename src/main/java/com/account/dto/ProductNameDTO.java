package com.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductNameDTO {

    private Integer id;
    private String productName;
    private CategoryDTO category;
    private CompanyDTO company;
    private Boolean enabled;

}
