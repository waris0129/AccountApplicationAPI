package com.account.controller;

import com.account.dto.InvoiceDTO1;
import com.account.dto.ProfitDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.Invoice1Service;
import com.account.service.ProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/profit")
public class ProfitController {

    @Autowired
    private ProfitService profitService;
    @Autowired
    private Invoice1Service invoice1Service;

    @GetMapping()
    public String getProfitObject(Model model) {

        List<InvoiceDTO1> salesInvoiceList = invoice1Service.findAllSalesInvoiceByCompanyId_SavedStatus(2);
        List<ProfitDTO> profitDTOList = profitService.getAllProfit();

        model.addAttribute("salesInvoiceList",salesInvoiceList);
        model.addAttribute("profitList",profitDTOList);
        model.addAttribute("invoiceNo",new InvoiceDTO1());

        return "profit/profit";
    }

    @PostMapping("/save")
    public String saveProfit(InvoiceDTO1 invoiceDTO1) throws AccountingApplicationException {

        ProfitDTO profitDTO = profitService.saveProfitTransaction(invoiceDTO1.getInvoiceNo());

        return "redirect:/profit";
    }

    @GetMapping("/print/invoiceNo/{invoiceNo}")
    public String printProfit(@PathVariable("invoiceNo") String invoiceNo, Model model){

        ProfitDTO profitDTO = profitService.findProfitByInvoiceId(invoiceNo);

        model.addAttribute("profit",profitDTO);


        return "profit/print";
    }





}
