package com.account.service;

import com.account.dto.CategoryDTO;
import com.account.entity.Category;
import com.account.exceptionHandler.AccountingApplicationException;

import java.util.List;

public interface CategoryService {

    CategoryDTO saveCategory(CategoryDTO categoryDTO) throws AccountingApplicationException;
    CategoryDTO findCategory(String description) throws AccountingApplicationException;
    CategoryDTO deleteCategory(String description) throws AccountingApplicationException;
    List<CategoryDTO> getAllCategories();
    List<CategoryDTO> getAllCategoriesByCompany(Integer companyID);

}
