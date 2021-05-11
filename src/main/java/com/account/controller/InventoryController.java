package com.account.controller;

import com.account.dto.CategoryDTO;
import com.account.dto.CompanyDTO;
import com.account.dto.ProductNameDTO;
import com.account.dto.UserDto;
import com.account.enums.Unit;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.CategoryService;
import com.account.service.CompanyService;
import com.account.service.ProductNameService;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@PreAuthorize("hasAnyAuthority({'Manager'})")
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProductNameService productNameService;
    @Autowired
    private UserService userService;


    private Integer getLoginCompanyId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto securityUser  = null;
        try {
            securityUser = this.userService.getUser(username);
        } catch (UserNotFoundInSystem userNotFoundInSystem) {
            userNotFoundInSystem.printStackTrace();
        } catch (AccountingApplicationException e) {
            e.printStackTrace();
        }
        Integer id =  securityUser.getCompany().getId();

        return id;
    }




    String error = null;

    @GetMapping("/category-registration")
    public CategoryDTO emptyCategory(Model model) throws CompanyNotFoundException {

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCompany(companyService.findById(getLoginCompanyId()));

        return categoryDTO;


    }


    @PostMapping("/category-registration")
    public ResponseEntity<ResponseWrapper> saveCategory(@RequestBody CategoryDTO categoryDTO, Model model) throws CompanyNotFoundException, AccountingApplicationException {

        categoryDTO.setCompany(companyService.findById(getLoginCompanyId()));

        CategoryDTO createCategoryDTO = categoryService.saveCategory(categoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Category is created").data(createCategoryDTO).build());
    }


    @GetMapping("/delete/{category}")
    public ResponseEntity<ResponseWrapper> deleteCategory(@PathVariable("category") String category) throws AccountingApplicationException {

        CategoryDTO deleteCategoryDTO = categoryService.deleteCategory(category);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Category is deleted").data(deleteCategoryDTO.getCategory()).build());
    }

    @GetMapping("/all-category-list")
    public ResponseEntity<ResponseWrapper> CategoryList(){

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoriesByCompany(getLoginCompanyId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get Category List").data(categoryDTOList).build());
    }


    @GetMapping("/product-registration")
    public ProductNameDTO getEmptyProductNameObject(Model model) throws CompanyNotFoundException {

        ProductNameDTO productNameDTO = new ProductNameDTO();
        CompanyDTO companyDTO = companyService.findById(getLoginCompanyId());

        productNameDTO.setCompany(companyDTO);

        return productNameDTO;
    }



    @PostMapping("/product-registration")
    public ResponseEntity<ResponseWrapper> saveProductName(@RequestBody ProductNameDTO productNameDTO,@RequestParam String category) throws AccountingApplicationException, CompanyNotFoundException {

        productNameDTO.setCompany(companyService.findById(getLoginCompanyId()));
        productNameDTO.setCategory(categoryService.findCategory(category));

        ProductNameDTO productNameDTO1 =  productNameService.saveProductNameDTO(productNameDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Product is created").data(productNameDTO1).build());
    }



    @GetMapping("/product-update/{productName}")
    public String updateProductObject(@PathVariable("productName") String productName, Model model) throws AccountingApplicationException {

        ProductNameDTO productNameDTO = productNameService.findProductNameDTO(productName);

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoriesByCompany(getLoginCompanyId());
        List<Unit>unitList = Arrays.asList(Unit.values());
        List<ProductNameDTO>productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());

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
    public ResponseEntity<ResponseWrapper> deleteProduct(@PathVariable("productName") String productName) throws AccountingApplicationException {

        ProductNameDTO productNameDTO = productNameService.deleteProductNameDTO(productName);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Product is deleted").data(productNameDTO.getProductName()).build());
    }



    @GetMapping("/all-product-list")
    public ResponseEntity<ResponseWrapper> productList() throws CompanyNotFoundException, AccountingApplicationException {

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get Product List").data(productNameDTOList).build());
    }

}
