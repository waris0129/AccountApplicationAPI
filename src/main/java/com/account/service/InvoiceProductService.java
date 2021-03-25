package com.account.service;

import com.account.dto.InvoiceProductDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;

import java.math.BigDecimal;

public interface InvoiceProductService {

    InvoiceProductDTO createNewInvoiceProductTemplate(String vendorName, String invoiceType) throws CompanyNotFoundException, UserNotFoundInSystem;
    InvoiceProductDTO addProductItem(String invoiceNumber, String inventoryNo, BigDecimal price, Integer qty) throws CompanyNotFoundException, AccountingApplicationException;
    InvoiceProductDTO getInvoice(String invoiceNo) throws AccountingApplicationException;
}
