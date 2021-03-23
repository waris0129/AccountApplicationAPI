package com.account.controller;

import com.account.dto.InvoiceDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    @PostMapping("/new")
    public InvoiceDTO createNewInvoice(@RequestParam String vendor, @RequestParam String type) throws UserNotFoundInSystem {

        InvoiceDTO invoiceDTO = invoiceService.createNewInvoiceTemplate(vendor,type);

        return invoiceDTO;
    }

    @PutMapping("/update/{invoiceNumber}")
    public InvoiceDTO updateInvoice1(@PathVariable("invoiceNumber") String invoiceNumber,@RequestBody InvoiceDTO invoiceDTO) throws AccountingApplicationException {

        InvoiceDTO updateDto = invoiceService.updateInvoiceStatus(invoiceNumber,invoiceDTO);

        return updateDto;
    }

    @PutMapping("/update")
    public InvoiceDTO updateInvoice2(@RequestParam String invoiceNumber, @RequestParam String status) throws AccountingApplicationException {

        InvoiceDTO updateDto = invoiceService.updateInvoiceStatus(invoiceNumber,status);

        return updateDto;
    }




}
