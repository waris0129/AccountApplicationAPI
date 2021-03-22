package com.account.controller;

import com.account.dto.InvoiceProductDTO;
import com.account.entity.InvoiceNumber;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.InvoiceNumberService;
import com.account.service.InvoiceProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceNumberService invoiceNumberService;
    @Autowired
    private InvoiceProductService invoiceProductService;

    @GetMapping("/invoice-number")
    public String createInvoiceNumber(){

        return invoiceNumberService.createInvoiceNumber();
    }


    @GetMapping("/add-invoice")
    public InvoiceProductDTO createInvoice(Model model) throws CompanyNotFoundException {
        return invoiceProductService.createInvoiceView();
    }

}
