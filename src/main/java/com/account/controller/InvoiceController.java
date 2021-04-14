package com.account.controller;

import com.account.dto.InvoiceDTO1;
import com.account.dto.ProductDTO;
import com.account.dto.ProductNameDTO;
import com.account.dto.VendorDTO;
import com.account.enums.InvoiceType;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private Invoice1Service invoice1Service;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProductNameService productNameService;


    @GetMapping("/create-invoice")
    public String getInvoiceObject(Model model){
        InvoiceDTO1 invoiceDTO1 = new InvoiceDTO1();

        List<VendorDTO> vendorDTOList = vendorService.getAllActiveVendorByCompany(2);
        List<InvoiceType> invoiceTypeList = Arrays.asList(InvoiceType.values());
        List<InvoiceDTO1> invoiceDTO1List = invoice1Service.findAllInvoiceByCompanyId(2);

        model.addAttribute("invoice",invoiceDTO1);
        model.addAttribute("vendors",vendorDTOList);
        model.addAttribute("invoiceTypes",invoiceTypeList);
        model.addAttribute("invoiceList",invoiceDTO1List);

        return "invoice/create-invoice";
    }




    @PostMapping("/create-invoice")
    public String createInvoice(InvoiceDTO1 invoiceDTO1,Model model) throws AccountingApplicationException, UserNotFoundInSystem, CompanyNotFoundException {

        InvoiceDTO1 invoiceDTO = invoice1Service.createNewInvoiceTemplate(invoiceDTO1.getVendor().getCompanyName(),invoiceDTO1.getInvoiceType().getValue());

        return "redirect:/invoice/create-invoice";
    }



    @GetMapping("/delete-invoice/{invoiceNo}")
    public String deleteInvoice(@PathVariable("invoiceNo") String invoiceNo) throws AccountingApplicationException {

        invoice1Service.cancelInvoice(invoiceNo);

        return "redirect:/invoice/create-invoice";
    }




    @GetMapping("/create-purchase")
    public String getPurchaseInvoiceObject(Model model){

        List<InvoiceDTO1> purchaseInvoiceNoList = invoice1Service.findAllPurchaseInvoiceByCompanyId(2);
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);

        model.addAttribute("purchaseInvoiceNoList",purchaseInvoiceNoList);
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("qty", new String());
        model.addAttribute("price",new String());
        model.addAttribute("productNameDTO",new ProductNameDTO());
        model.addAttribute("invoiceNumber",new String());


        return "invoice/purchase-invoice";
    }


    @PostMapping("/create-purchase")
    public String AddItemPurchase(Model model) throws CompanyNotFoundException, AccountingApplicationException {

        String invoiceNumber = (String) model.getAttribute("invoiceNumber");
        String productDTO   = (String) model.getAttribute("productNameDTO");
        Integer price = (Integer) model.getAttribute("price");
        Integer qty = (Integer) model.getAttribute("qty");

        invoice1Service.addProductItem(invoiceNumber,productDTO,price,qty);


        return "invoice/purchase-invoice";
    }



}
