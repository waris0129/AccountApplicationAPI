package com.account.controller;

import com.account.entity.InvoiceNumber;
import com.account.service.InvoiceNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceNumberService invoiceNumberService;

    @GetMapping("/invoice-number")
    public String createInvoiceNumber(){

        return invoiceNumberService.createInvoiceNumber();
    }

}
