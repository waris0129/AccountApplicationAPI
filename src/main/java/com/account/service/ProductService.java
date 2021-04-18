package com.account.service;


import com.account.dto.CategoryDTO;
import com.account.dto.ProductDTO;
import com.account.dto.ProductNameDTO;
import com.account.entity.Product;
import com.account.exceptionHandler.AccountingApplicationException;

import java.util.List;

public interface ProductService {

    ProductDTO saveProduct(ProductDTO productDTO) throws AccountingApplicationException;
    ProductDTO saveProductByPara(ProductDTO productDTO, Integer price, Integer qty, String name) throws AccountingApplicationException;
    List<ProductDTO> findProductByName(String name) throws AccountingApplicationException;
    ProductDTO findProductByInventoryNo(String inventoryNo) throws AccountingApplicationException;
    ProductDTO updateProduct(String name,ProductDTO productDTO) throws AccountingApplicationException;
    ProductDTO deleteProduct(String name) throws AccountingApplicationException;
    List<ProductDTO> findAllProduct();
    ProductDTO deleteProduct(String invoiceNumber,String inventoryNo) throws AccountingApplicationException;





}
