package com.account.service;

import com.account.dto.InvoiceProductDTO;
import com.account.dto.ProductDTO;
import com.account.entity.Product;
import com.account.exceptionHandler.CompanyNotFoundException;

import java.util.List;

public interface InvoiceProductService {

    InvoiceProductDTO createInvoiceView() throws CompanyNotFoundException;
    InvoiceProductDTO addProductItem(ProductDTO productDTO) throws CompanyNotFoundException;

}
