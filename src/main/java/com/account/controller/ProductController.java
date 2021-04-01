package com.account.controller;


import com.account.dto.ProductDTO;
import com.account.dto.ProductNameDTO;
import com.account.entity.ProductName;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.ProductService;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
@PreAuthorize("hasAnyAuthority({'Manager','Admin'})")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/new")
    public ProductDTO getEmptyObject(){
        return new ProductDTO();
    }


//    @PostMapping("/new")
//    public ResponseEntity<ResponseWrapper> saveProduct(@RequestBody ProductDTO productDTO) throws AccountingApplicationException {
//
//        ProductDTO productDTO1 = productService.saveProduct(productDTO);
//        return ResponseEntity.ok(ResponseWrapper.builder().code(201).success(true).message("Product is created successfully").data(productDTO1).build());
//    }


    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> saveProductByPara(@RequestBody ProductDTO productDTO, @RequestParam Integer price, @RequestParam Integer qty, @RequestParam String name) throws AccountingApplicationException {

        ProductDTO productDTO1 = productService.saveProductByPara(productDTO, price, qty, name);
        return ResponseEntity.ok(ResponseWrapper.builder().code(201).success(true).message("Product is created successfully").data(productDTO1).build());
    }


    @GetMapping("/{name}")
    public ResponseEntity<ResponseWrapper> findProduct(@PathVariable("name") String name) throws AccountingApplicationException {

        List<ProductDTO> productDTO1 = productService.findProductByName(name);
        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("Product is found successfully").data(productDTO1).build());
    }

    @GetMapping("/inventory/{inventoryNo}")
    public ResponseEntity<ResponseWrapper> findProductByInventoryNo(@PathVariable("inventoryNo") String inventoryNo) throws AccountingApplicationException {

        ProductDTO productDTO1 = productService.findProductByInventoryNo(inventoryNo);
        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("Product is found successfully").data(productDTO1).build());
    }

    @PutMapping("/update/{name}")
    public ResponseEntity<ResponseWrapper> updateProduct(@PathVariable("name") String name,@RequestBody ProductDTO productDTO) throws AccountingApplicationException {

        ProductDTO productDTO1 = productService.updateProduct(name,productDTO);
        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("Product is updated successfully").data(productDTO1).build());
    }


    @DeleteMapping("/delete/{name}")
    public ResponseEntity<ResponseWrapper> deleteProduct(@PathVariable("name") String name) throws AccountingApplicationException {

        ProductDTO productDTO1 = productService.deleteProduct(name);
        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("Product is deleted successfully").build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> allProduct() {

        List<ProductDTO> productDTO1 = productService.findAllProduct();
        return ResponseEntity.ok(ResponseWrapper.builder().code(200).success(true).message("Get product list successfully").data(productDTO1).build());
    }


}
