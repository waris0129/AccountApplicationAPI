package com.account.controller;

import com.account.dto.InvoiceDTO1;
import com.account.dto.ProfitDTO;
import com.account.dto.UserDto;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.Invoice1Service;
import com.account.service.ProfitService;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserService userService;


    private Integer getLoginCompanyId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto securityUser  = null;
        try {
            securityUser = this.userService.getUser(username);
        } catch (UserNotFoundInSystem userNotFoundInSystem) {
            userNotFoundInSystem.printStackTrace();
        } catch (AccountingApplicationException e) {
            e.printStackTrace();
        }
        Integer id =  securityUser.getCompany().getId();

        return id;
    }





    @GetMapping()
    public String getProfitObject(Model model) {

        List<InvoiceDTO1> salesInvoiceList = invoice1Service.findAllSalesInvoiceByCompanyId_SavedStatus(getLoginCompanyId());
        List<ProfitDTO> profitDTOList = profitService.getAllProfit(getLoginCompanyId());

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
        List<ProfitDTO> profitDTOList = profitService.getAllProfit(getLoginCompanyId());

        model.addAttribute("profitList",profitDTOList);

        model.addAttribute("profit",profitDTO);


        return "profit/print";
    }





}
