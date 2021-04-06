package com.account.controller;

import com.account.dto.CategoryDTO;
import com.account.dto.ProductNameDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.CategoryService;
import com.account.service.ProductNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productRegister")
public class ProductNameController {


    @Autowired
    private ProductNameService ProductNameService;

    @GetMapping("/new")
    public ProductNameDTO emptyObject(){
        return new ProductNameDTO();
    }


    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> saveProductName(@RequestBody ProductNameDTO productNameDTO) throws AccountingApplicationException {

        ProductNameDTO dto = ProductNameService.saveProductNameDTO(productNameDTO);

        return ResponseEntity.ok(ResponseWrapper.builder().code(201).success(true).message("Product is registered successfully").data(dto).build());
    }


    @GetMapping("/{productName}")
    public ResponseEntity<ResponseWrapper> findProductName(@PathVariable("productName") String productName) throws AccountingApplicationException {

        ProductNameDTO dto = ProductNameService.findProductNameDTO(productName);

        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("The Product found successfully").data(dto).build());
    }


    @DeleteMapping("/delete/{productName}")
    public ResponseEntity<ResponseWrapper> deleteProductName(@PathVariable("productName") String productName) throws AccountingApplicationException {

        ProductNameDTO dto = ProductNameService.deleteProductNameDTO(productName);

        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("The Product is deleted successfully").build());
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> findAllProductName(){

        List<ProductNameDTO> productNameDTOList = ProductNameService.getAllProductNameDTOList();

        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("Get Product list successfully").data(productNameDTOList).build());
    }
}
