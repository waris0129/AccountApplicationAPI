package com.account.controller;


import com.account.dto.InvoiceDTO1;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.Invoice1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice1")
public class Invoice1Controller {

    @Autowired
    private Invoice1Service invoice1Service;


    @GetMapping("/createInvoiceNumber")
    public String createInvoiceNumber(@RequestParam String invoiceType){
        return invoice1Service.createInvoiceNumber(invoiceType);
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> createNewInvoice(@RequestParam String vendor, @RequestParam String type) throws UserNotFoundInSystem, AccountingApplicationException {

        InvoiceDTO1 invoiceDTO = invoice1Service.createNewInvoiceTemplate(vendor,type);

        return ResponseEntity.status(201).body(ResponseWrapper.builder().code(201).success(true).message("Open new invoice successfully").data(invoiceDTO).build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> updateInvoice(@RequestParam String invoiceNumber, @RequestParam String status) throws AccountingApplicationException {

        InvoiceDTO1 updateDto = invoice1Service.updateInvoiceStatus(invoiceNumber,status);

        return ResponseEntity.status(200).body(ResponseWrapper.builder().code(200).success(true).message("Update invoice successfully").data(updateDto).build());
    }


    @DeleteMapping("/delete/{invoiceNumber}")
    public ResponseEntity<ResponseWrapper> deleteInvoice(@PathVariable("invoiceNumber") String invoiceNumber) throws AccountingApplicationException {
        InvoiceDTO1 cancelInvoice = invoice1Service.cancelInvoice(invoiceNumber);

        return ResponseEntity.status(200).body(ResponseWrapper.builder().code(200).success(true).message("Invoice cancelled successfully").build());
    }


    @GetMapping("/find/{invoiceNumber}")
    public ResponseEntity<ResponseWrapper> findInvoice(@PathVariable("invoiceNumber") String invoiceNumber) throws AccountingApplicationException {
        InvoiceDTO1 findInvoice = invoice1Service.findInvoice(invoiceNumber);

        return ResponseEntity.status(200).body(ResponseWrapper.builder().code(200).success(true).message("Invoice found successfully").data(findInvoice).build());
    }

    @PostMapping("/new/{invoiceNo}")
    public ResponseEntity<ResponseWrapper> addItem(@PathVariable("invoiceNo") String invoiceNo, @RequestParam String productName, @RequestParam Integer price, @RequestParam Integer qty) throws CompanyNotFoundException, AccountingApplicationException {
        InvoiceDTO1 saveItem = invoice1Service.addProductItem(invoiceNo,productName,price,qty);

        return ResponseEntity.status(201).body(ResponseWrapper.builder().code(201).success(true).message("Item is saved successfully").data(saveItem).build());
    }


}
