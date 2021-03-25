package com.account.controller;

import com.account.dto.InvoiceDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.InvoiceService;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> createNewInvoice(@RequestParam String vendor, @RequestParam String type) throws UserNotFoundInSystem {

        InvoiceDTO invoiceDTO = invoiceService.createNewInvoiceTemplate(vendor,type);

        return ResponseEntity.status(201).body(ResponseWrapper.builder().code(201).success(true).message("Open new invoice successfully").data(invoiceDTO).build());
    }

    @PutMapping("/update/{invoiceNumber}")
    public ResponseEntity<ResponseWrapper> updateInvoice1(@PathVariable("invoiceNumber") String invoiceNumber,@RequestBody InvoiceDTO invoiceDTO) throws AccountingApplicationException {

        InvoiceDTO updateDto = invoiceService.updateInvoiceStatus(invoiceNumber,invoiceDTO);

        return ResponseEntity.status(200).body(ResponseWrapper.builder().code(200).success(true).message("Update invoice successfully").data(updateDto).build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> updateInvoice2(@RequestParam String invoiceNumber, @RequestParam String status) throws AccountingApplicationException {

        InvoiceDTO updateDto = invoiceService.updateInvoiceStatus(invoiceNumber,status);

        return ResponseEntity.status(200).body(ResponseWrapper.builder().code(200).success(true).message("Update invoice successfully").data(updateDto).build());
    }

    @DeleteMapping("/delete/{invoiceNumber}")
    public ResponseEntity<ResponseWrapper> deleteInvoice(@PathVariable("invoiceNumber") String invoiceNumber) throws AccountingApplicationException {
        InvoiceDTO cancelInvoice = invoiceService.cancelInvoice(invoiceNumber);

        return ResponseEntity.status(200).body(ResponseWrapper.builder().code(200).success(true).message("Invoice cancelled successfully").build());
    }



    @GetMapping("/find/{invoiceNumber}")
    public ResponseEntity<ResponseWrapper> findInvoice(@PathVariable("invoiceNumber") String invoiceNumber) throws AccountingApplicationException {
        InvoiceDTO findInvoice = invoiceService.findInvoice(invoiceNumber);

        return ResponseEntity.status(200).body(ResponseWrapper.builder().code(200).success(true).message("Invoice found successfully").data(findInvoice).build());
    }




}
