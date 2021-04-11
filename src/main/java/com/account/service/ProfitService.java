package com.account.service;


import com.account.dto.InvoiceDTO1;
import com.account.dto.ProductDTO;
import com.account.dto.ProfitDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;

import java.util.List;

public interface ProfitService {

    List<ProductDTO> updateInventoryByFIFO(String salesInvoiceNumber, String productName, Integer soldStock,Integer salesPrice) throws AccountingApplicationException;
    String createInvoiceNumber(String invoiceType) throws CompanyNotFoundException;
    InvoiceDTO1 createNewInvoiceTemplate(String vendorName, String invoiceType) throws UserNotFoundInSystem, AccountingApplicationException, CompanyNotFoundException;
    ProfitDTO saveProfitTransaction(String invoiceNumber) throws AccountingApplicationException;

}
