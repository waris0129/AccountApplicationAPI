package com.account.controller;

import com.account.dto.InvoiceDTO1;
import com.account.dto.ProductDTO;
import com.account.dto.ProfitDTO;
import com.account.entity.Product;
import com.account.entity.Profit;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/profit")
public class ProfitController {

    @Autowired
    private ProfitService profitService;


    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> createNewSalesInvoice(@RequestParam String vendor, @RequestParam String type) throws UserNotFoundInSystem, AccountingApplicationException, CompanyNotFoundException {

        InvoiceDTO1 invoiceDTO = profitService.createNewInvoiceTemplate(vendor,type);

        return ResponseEntity.status(201).body(ResponseWrapper.builder().code(201).success(true).message("Open new invoice successfully").data(invoiceDTO).build());
    }


    @PutMapping("/update/{salesInvoiceNumber}")
    public List<ProductDTO> updateInventoryByFIFO(@PathVariable("salesInvoiceNumber") String salesInvoiceNumber, @RequestParam String productName, @RequestParam Integer totalSoldItem, @RequestParam Integer salesPrice) throws AccountingApplicationException {
        List<ProductDTO> updateProductFIFO = profitService.updateInventoryByFIFO(salesInvoiceNumber,productName,totalSoldItem,salesPrice);
        return updateProductFIFO;
    }


    @PostMapping("/new/{invoiceNumber}")
    public ResponseEntity<ResponseWrapper> createNewSalesInvoice(@PathVariable("invoiceNumber") String invoiceNumber) throws AccountingApplicationException {

        ProfitDTO profitDTO = profitService.saveProfitTransaction(invoiceNumber);

        return ResponseEntity.status(201).body(ResponseWrapper.builder().code(201).success(true).message("Sales and Profit are created ").data(profitDTO).build());
    }







}
