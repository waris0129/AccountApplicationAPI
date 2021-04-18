package com.account.controller;

import com.account.dto.*;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.*;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ProductService productService;





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

        PurchaseInvoiceDTO purchaseInvoiceDTO = new PurchaseInvoiceDTO();
        List<InvoiceDTO1> purchaseInvoiceNoList = invoice1Service.findAllPurchaseInvoiceByCompanyId_NoSavedStatus(2);
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);
        InvoiceDTO1 invoiceDTO1 = new InvoiceDTO1();

        model.addAttribute("purchaseInvoiceNoList",purchaseInvoiceNoList);
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("purchaseInvoiceDTO",purchaseInvoiceDTO);
        model.addAttribute("selectedInvoice",invoiceDTO1);

        return "invoice/purchase-invoice";
    }

    @GetMapping("/review-invoice")
    public String reviewInvoice(@Param("invoiceNo")String invoiceNo,Model model) throws AccountingApplicationException {

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNo);


        PurchaseInvoiceDTO purchaseInvoiceDTO = new PurchaseInvoiceDTO();
        List<InvoiceDTO1> purchaseInvoiceNoList = invoice1Service.findAllPurchaseInvoiceByCompanyId_NoSavedStatus(2);
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);


        model.addAttribute("purchaseInvoiceNoList",purchaseInvoiceNoList);
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("purchaseInvoiceDTO",purchaseInvoiceDTO);
        model.addAttribute("selectedInvoice",invoiceDTO1);

        return "redirect:/invoice/purchase-invoice";
    }


    @PostMapping("/add-item-purchase")
    public String AddItemPurchase(@ModelAttribute("purchaseInvoiceDTO") PurchaseInvoiceDTO purchaseInvoiceDTO,Model model) throws CompanyNotFoundException, AccountingApplicationException {

        String invoiceNumber = purchaseInvoiceDTO.getInvoiceNumber();
        String productDTO   =  purchaseInvoiceDTO.getProductNameDTO();
        Integer price =  purchaseInvoiceDTO.getPrice();
        Integer qty =  purchaseInvoiceDTO.getQty();
        InvoiceDTO1 invoiceDTO1 = null;

        if(productDTO!=null)
             invoiceDTO1 = invoice1Service.addProductItem(invoiceNumber,productDTO,price,qty);
        else
            invoiceDTO1 = invoice1Service.findInvoice(invoiceNumber);

        PurchaseInvoiceDTO purchaseInvoiceDTO2 = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO2.setInvoiceNumber(invoiceNumber);

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);
        List<ProductDTO> productList = invoiceDTO1.getProductList();

        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("purchaseInvoiceDTO",purchaseInvoiceDTO2);
        model.addAttribute("selectInvoice",invoiceDTO1);
        model.addAttribute("productList",productList);

        return "invoice/add-item-purchase";
    }

    private static String invoiceNumberFromDeleteAddItem = null;
    @GetMapping("/delete-add-item")
    public String deleteAddItem(@Param("inventoryName") String inventoryName, @Param("invoiceNo") String invoiceNo, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        productService.deleteProduct(invoiceNo,inventoryName);

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNo);

        PurchaseInvoiceDTO purchaseInvoiceDTO = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO.setInvoiceNumber(invoiceNo);

        invoiceNumberFromDeleteAddItem = invoiceNo;
        return "redirect:/invoice/add-item-purchase";
    }

    @GetMapping("/add-item-purchase")
    public String getAddItemPurchase(Model model) throws CompanyNotFoundException, AccountingApplicationException {

        InvoiceDTO1 invoiceDTO1 = invoiceDTO1 = invoice1Service.findInvoice(invoiceNumberFromDeleteAddItem);

        PurchaseInvoiceDTO purchaseInvoiceDTO2 = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO2.setInvoiceNumber(invoiceNumberFromDeleteAddItem);

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);
        List<ProductDTO> productList = invoiceDTO1.getProductList();

        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("purchaseInvoiceDTO",purchaseInvoiceDTO2);
        model.addAttribute("selectInvoice",invoiceDTO1);
        model.addAttribute("productList",productList);

        return "invoice/add-item-purchase";
    }



    @GetMapping("/update-invoice-status")
    public String updateInvoiceStatus(@Param("invoiceNo")String invoiceNo, @Param("status") String status) throws AccountingApplicationException {

        invoice1Service.updateInvoiceStatus(invoiceNo,status);


        return "redirect:/invoice/saved-purchase-invoice";
    }


    @GetMapping("/saved-purchase-invoice")
    public String savedPurchaseInvoice(Model model) throws AccountingApplicationException {

        List<InvoiceDTO1> invoiceDTO1List = invoice1Service.findAllPurchaseInvoiceByCompanyId_SavedStatus(2);


        model.addAttribute("invoiceList",invoiceDTO1List);


        return "invoice/saved-purchase-invoice";
    }




}
