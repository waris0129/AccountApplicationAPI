package com.account.service;


import com.account.dto.ProductDTO;
import com.account.entity.Product;

public interface ProductService {

    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO findProduct();


}
