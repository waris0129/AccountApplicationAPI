package com.account.service;


import com.account.dto.InvoiceDTO1;
import com.account.entity.Invoice1;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Invoice1Service {
    String createInvoiceNumber(String invoiceType) throws CompanyNotFoundException;
    InvoiceDTO1 createNewInvoiceTemplate(String vendorName, String invoiceType) throws UserNotFoundInSystem, AccountingApplicationException, CompanyNotFoundException;
    InvoiceDTO1 updateInvoiceStatus(String invoiceNumber, String status) throws AccountingApplicationException;
    InvoiceDTO1 cancelInvoice(String invoiceNumber) throws AccountingApplicationException;
    InvoiceDTO1 findInvoice(String invoiceNumber) throws AccountingApplicationException;
    InvoiceDTO1 addProductItem(String invoiceNumber, String inventoryNo, Integer price, Integer qty) throws CompanyNotFoundException, AccountingApplicationException;

    List<InvoiceDTO1> findAllInvoiceByCompanyId(Integer companyId);
    List<InvoiceDTO1> findAllSalesInvoiceByCompanyId(Integer companyId);
    List<InvoiceDTO1> findAllPurchaseInvoiceByCompanyId(Integer companyId);
    List<InvoiceDTO1> findAllPurchaseInvoiceByCompanyId_NoSavedStatus(Integer companyId);
    List<InvoiceDTO1> findAllPurchaseInvoiceByCompanyId_SavedStatus(Integer companyId);
    Integer calculateTotalCost(String invoiceNumber);
    Invoice1 updateTotalCost(String invoiceNumber);
    List<InvoiceDTO1> findAllInvoiceByCompanyId_NoSavedStatus(Integer companyId);
    List<InvoiceDTO1> findAllSalesInvoiceByCompanyId_NoSavedStatus(Integer companyId);
    List<InvoiceDTO1> findAllSalesInvoiceByCompanyId_SavedStatus(Integer companyId);
}
