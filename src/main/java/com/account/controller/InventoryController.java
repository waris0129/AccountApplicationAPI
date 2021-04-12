package com.account.controller;

import com.account.dto.CategoryDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.CategoryService;
import com.account.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CompanyService companyService;

    @GetMapping("/category-registration")
    public String emptyCategory(Model model) throws CompanyNotFoundException {

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCompany(companyService.findById(2));

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoriesByCompany(2);

        model.addAttribute("category",categoryDTO);
        model.addAttribute("categoryList",categoryDTOList);

        return "inventory/category-registration";


    }


    @PostMapping("/category-registration")
    public String saveCategory(@ModelAttribute("category") CategoryDTO categoryDTO) throws CompanyNotFoundException, AccountingApplicationException {

        categoryDTO.setCompany(companyService.findById(2));

        categoryService.saveCategory(categoryDTO);

        return "redirect:/inventory/category-registration";


    }


    @GetMapping("/delete/{category}")
    public String deleteCategory(@PathVariable("category") String category) throws AccountingApplicationException {

        categoryService.deleteCategory(category);

        return "redirect:/inventory/category-registration";


    }


}
