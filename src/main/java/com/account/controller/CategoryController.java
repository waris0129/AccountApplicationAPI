package com.account.controller;

import com.account.dto.CategoryDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@PreAuthorize("hasAnyAuthority({'Manager','Admin'})")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/new")
    public CategoryDTO emptyObject(){
        return new CategoryDTO();
    }


    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> saveCategory(@RequestBody CategoryDTO categoryDTO) throws AccountingApplicationException {

        CategoryDTO dto = categoryService.saveCategory(categoryDTO);

        return ResponseEntity.ok(ResponseWrapper.builder().code(201).success(true).message("Add new category successfully").data(dto).build());
    }


    @GetMapping("/{category}")
    public ResponseEntity<ResponseWrapper> findCategory(@PathVariable("category") String category) throws AccountingApplicationException {

        CategoryDTO dto = categoryService.findCategory(category);

        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("The category found successfully").data(dto).build());
    }


    @DeleteMapping("/delete/{category}")
    public ResponseEntity<ResponseWrapper> deleteCategory(@PathVariable("category") String category) throws AccountingApplicationException {

        CategoryDTO dto = categoryService.deleteCategory(category);

        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("The category is deleted successfully").build());
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> findAllCategories(){

        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("Get category list successfully").data(categoryDTOList).build());
    }



}
