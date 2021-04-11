package com.account.service;


import com.account.dto.InvoiceDTO1;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;

public interface Invoice1Service {
    String createInvoiceNumber(String invoiceType) throws CompanyNotFoundException;
    InvoiceDTO1 createNewInvoiceTemplate(String vendorName, String invoiceType) throws UserNotFoundInSystem, AccountingApplicationException, CompanyNotFoundException;
    InvoiceDTO1 updateInvoiceStatus(String invoiceNumber, String status) throws AccountingApplicationException;
    InvoiceDTO1 cancelInvoice(String invoiceNumber) throws AccountingApplicationException;
    InvoiceDTO1 findInvoice(String invoiceNumber) throws AccountingApplicationException;
    InvoiceDTO1 addProductItem(String invoiceNumber, String inventoryNo, Integer price, Integer qty) throws CompanyNotFoundException, AccountingApplicationException;
}
