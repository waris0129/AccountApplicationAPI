package com.account.controller;

import com.account.dto.InvoiceProductDTO;
import com.account.dto.SingleInvoiceProductDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.InvoiceNumberService;
import com.account.service.InvoiceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/invoice-product")
public class InvoiceProductController {

    @Autowired
    private InvoiceNumberService invoiceNumberService;
    @Autowired
    private InvoiceProductService invoiceProductService;

    @GetMapping("/invoice-number")
    public String createInvoiceNumber(){

        return invoiceNumberService.createInvoiceNumber();
    }


    @GetMapping("/add-invoice/{vendorName}")
    public InvoiceProductDTO createInvoice(@PathVariable("vendorName") String vendorName) throws CompanyNotFoundException, UserNotFoundInSystem {
        return invoiceProductService.createNewInvoiceProductTemplate(vendorName);
    }

    @PostMapping("/add-item/{invoiceNo}")
    public InvoiceProductDTO addItem(@PathVariable("invoiceNo") String invoiceNo,@RequestBody SingleInvoiceProductDTO singleInvoiceProductDTO) throws CompanyNotFoundException {
        return invoiceProductService.addProductItem(invoiceNo,singleInvoiceProductDTO);
    }

    @GetMapping("/get-invoice/{invoiceNo}")
    public InvoiceProductDTO getInvoice(@PathVariable("invoiceNo")String invoiceNo) throws AccountingApplicationException {
        return invoiceProductService.getInvoice(invoiceNo);
    }

}
