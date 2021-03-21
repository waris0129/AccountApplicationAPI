package com.account.dto;

import com.account.entity.Company;
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
    private String category;
    private CompanyDTO company;
    private Boolean enabled;

}
