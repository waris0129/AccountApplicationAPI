package com.account.controller;

import com.account.dto.CategoryDTO;
import com.account.dto.CompanyDTO;
import com.account.dto.ProductNameDTO;
import com.account.enums.Unit;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.service.CategoryService;
import com.account.service.CompanyService;
import com.account.service.ProductNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProductNameService productNameService;

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


    @GetMapping("/product-registration")
    public String getEmptyProductNameObject(Model model) {

        ProductNameDTO productNameDTO = new ProductNameDTO();
        CompanyDTO companyDTO = null;
        try {
            companyDTO = companyService.findById(2);
        } catch (CompanyNotFoundException e) {
            model.addAttribute("errorMessage",e.getMessage());
        }
        productNameDTO.setCompany(companyDTO);
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoriesByCompany(2);
        List<Unit>unitList = Arrays.asList(Unit.values());
        List<ProductNameDTO>productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);

        model.addAttribute("product",productNameDTO);
        model.addAttribute("categoryList",categoryDTOList);
        model.addAttribute("unitList",unitList);
        model.addAttribute("company",companyDTO);
        model.addAttribute("productList",productNameDTOList);

        return "inventory/product-registration";
    }



    @PostMapping("/product-registration")
    public String saveProductName(@ModelAttribute("product") ProductNameDTO productNameDTO) throws AccountingApplicationException, CompanyNotFoundException {

        productNameDTO.setCompany(companyService.findById(2));

        productNameService.saveProductNameDTO(productNameDTO);


        return "redirect:/inventory/product-registration";
    }



    @GetMapping("/product-update/{productName}")
    public String updateProductObject(@PathVariable("productName") String productName, Model model) throws AccountingApplicationException {

        ProductNameDTO productNameDTO = productNameService.findProductNameDTO(productName);

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoriesByCompany(2);
        List<Unit>unitList = Arrays.asList(Unit.values());
        List<ProductNameDTO>productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);

        model.addAttribute("product",productNameDTO);
        model.addAttribute("categoryList",categoryDTOList);
        model.addAttribute("unitList",unitList);
        model.addAttribute("company",productNameDTO.getCompany());
        model.addAttribute("productList",productNameDTOList);

        return "inventory/product-update";
    }

    @PostMapping("/product-update/{productName}")
    public String udpateProductName(@PathVariable("productName") String productName,ProductNameDTO productNameDTO) throws AccountingApplicationException {

        productNameService.update(productName,productNameDTO);


        return "redirect:/inventory/product-registration";
    }


    @GetMapping("/product-delete/{productName}")
    public String deleteProduct(@PathVariable("productName") String productName) throws AccountingApplicationException {

        productNameService.deleteProductNameDTO(productName);


        return "redirect:/inventory/product-registration";
    }

}
