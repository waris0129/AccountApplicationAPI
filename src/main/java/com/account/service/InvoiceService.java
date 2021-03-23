package com.account.service;

import com.account.dto.InvoiceDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;

public interface InvoiceService {

    InvoiceDTO createNewInvoiceTemplate(String vendorName, String invoiceType) throws UserNotFoundInSystem;
    InvoiceDTO updateInvoiceStatus(String invoiceNumber, InvoiceDTO invoiceDTO) throws AccountingApplicationException;
    InvoiceDTO updateInvoiceStatus(String invoiceNumber, String status) throws AccountingApplicationException;
    InvoiceDTO cancelInvoice(String invoiceNumber);
    InvoiceDTO findInvoice(String invoiceNumber);


}
