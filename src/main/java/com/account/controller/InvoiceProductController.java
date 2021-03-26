package com.account.controller;

import com.account.dto.InvoiceProductDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.InvoiceNumberService;
import com.account.service.InvoiceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/invoice-product")
public class InvoiceProductController {

    @Autowired
    private InvoiceNumberService invoiceNumberService;
    @Autowired
    private InvoiceProductService invoiceProductService;


    @GetMapping("/new")
    public InvoiceProductDTO createInvoiceProductTemplate(@RequestParam String vendorName, @RequestParam String invoiceType) throws CompanyNotFoundException, UserNotFoundInSystem {
        return invoiceProductService.createNewInvoiceProductTemplate(vendorName,invoiceType);
    }

    @PostMapping("/new/{invoiceNo}")
    public InvoiceProductDTO addItem(@PathVariable("invoiceNo") String invoiceNo,@RequestParam String productName, @RequestParam BigDecimal price,@RequestParam Integer qty) throws CompanyNotFoundException, AccountingApplicationException {
        return invoiceProductService.addProductItem(invoiceNo,productName,price,qty);
    }



}
