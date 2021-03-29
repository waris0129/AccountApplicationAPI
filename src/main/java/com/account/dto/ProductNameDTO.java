package com.account.dto;

import com.account.entity.Category;
import com.account.enums.Unit;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductNameDTO {

    private Integer id;
    private String productName;
    private String description;
    private Unit unit;
    private Integer lowLimitAlert;
    private Integer tax;
    private CategoryDTO category;
    private CompanyDTO company;
    private Boolean enabled;
}
