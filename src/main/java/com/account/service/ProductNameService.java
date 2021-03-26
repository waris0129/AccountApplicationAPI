package com.account.service;



import com.account.dto.ProductDTO;
import com.account.dto.ProductNameDTO;
import com.account.exceptionHandler.AccountingApplicationException;

import java.util.List;

public interface ProductNameService {

    ProductNameDTO saveProductNameDTO(ProductNameDTO productNameDTO) throws AccountingApplicationException;
    ProductNameDTO findProductNameDTO(String productName) throws AccountingApplicationException;
    ProductNameDTO deleteProductNameDTO(String productName) throws AccountingApplicationException;
    List<ProductNameDTO> getAllProductNameDTOList();


}
