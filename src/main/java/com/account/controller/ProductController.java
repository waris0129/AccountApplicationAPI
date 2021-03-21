package com.account.controller;


import com.account.dto.ProductDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {


    @GetMapping("/new")
    public ProductDTO getEmptyObject(){
        return new ProductDTO();
    }

}
