package com.account.service;

import com.account.dto.InvoiceProductDTO;
import com.account.dto.SingleInvoiceProductDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;

public interface InvoiceProductService {

    InvoiceProductDTO createNewInvoiceProductTemplate(String vendorName) throws CompanyNotFoundException, UserNotFoundInSystem;
    InvoiceProductDTO addProductItem(String invoiceNumber, SingleInvoiceProductDTO singleInvoiceProductDTO) throws CompanyNotFoundException;
    InvoiceProductDTO getInvoice(String invoiceNo) throws AccountingApplicationException;
}
