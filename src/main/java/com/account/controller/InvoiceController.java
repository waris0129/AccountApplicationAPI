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

    @Autowired
    private ProfitService profitService;





    @GetMapping("/create-invoice")
    public String getInvoiceObject(Model model){
        InvoiceDTO1 invoiceDTO1 = new InvoiceDTO1();

        List<VendorDTO> vendorDTOList = vendorService.getAllActiveVendorByCompany(2);
        List<InvoiceType> invoiceTypeList = Arrays.asList(InvoiceType.values());
        List<InvoiceDTO1> invoiceDTO1List = invoice1Service.findAllInvoiceByCompanyId_NoSavedStatus(2);
        List<InvoiceDTO1> invoiceDTO1List_Purchase= invoiceDTO1List.stream().filter(p->p.getInvoiceType().equals(InvoiceType.PURCHASE)).collect(Collectors.toList());
        List<InvoiceDTO1> invoiceDTO1List_Sales= invoiceDTO1List.stream().filter(p->p.getInvoiceType().equals(InvoiceType.SALES)).collect(Collectors.toList());

        model.addAttribute("invoice",invoiceDTO1);
        model.addAttribute("vendors",vendorDTOList);
        model.addAttribute("invoiceTypes",invoiceTypeList);
        model.addAttribute("invoiceListPurchase",invoiceDTO1List_Purchase);
        model.addAttribute("invoiceListSales",invoiceDTO1List_Sales);

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

        if(productDTO!=null){
            invoiceDTO1 = invoice1Service.addProductItem(invoiceNumber,productDTO,price,qty);
            Integer totalQTY = invoiceDTO1.getProductList().stream().mapToInt(p->p.getQty()).sum();
            invoiceDTO1.setTotalQTY(totalQTY);
            invoice1Service.updateInvoice(invoiceNumber,invoiceDTO1);
        }

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

    private static String invoiceNumberFromOutside = null;
    @GetMapping("/delete-add-item")
    public String deleteAddItem(@Param("inventoryName") String inventoryName, @Param("invoiceNo") String invoiceNo, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        productService.deleteProduct(invoiceNo,inventoryName);

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNo);

        PurchaseInvoiceDTO purchaseInvoiceDTO = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO.setInvoiceNumber(invoiceNo);

        invoiceNumberFromOutside = invoiceNo;
        return "redirect:/invoice/add-item-purchase";
    }

    @GetMapping("/add-item-purchase")
    public String getAddItemPurchase(Model model) throws CompanyNotFoundException, AccountingApplicationException {

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNumberFromOutside);

        PurchaseInvoiceDTO purchaseInvoiceDTO2 = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO2.setInvoiceNumber(invoiceNumberFromOutside);

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);
        List<ProductDTO> productList = invoiceDTO1.getProductList();

        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("purchaseInvoiceDTO",purchaseInvoiceDTO2);
        model.addAttribute("selectInvoice",invoiceDTO1);
        model.addAttribute("productList",productList);

        return "invoice/add-item-purchase";
    }

    @GetMapping("/add-item-purchase/{invoiceNo}")
    public String getAddItemPurchase2(@PathVariable("invoiceNo") String invoiceNo,Model model) throws CompanyNotFoundException, AccountingApplicationException {

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNo);

        PurchaseInvoiceDTO purchaseInvoiceDTO2 = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO2.setInvoiceNumber(invoiceNo);

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

        InvoiceDTO1 invoiceDTO1 = invoice1Service.updateInvoiceStatus(invoiceNo,status);

        String view = invoiceDTO1.getInvoiceType().equals(InvoiceType.PURCHASE)?"redirect:/invoice/review-purchase":"redirect:/invoice/review-sales";

        return view;
    }


    @GetMapping("/review-purchase")
    public String savedPurchaseInvoice(Model model) throws AccountingApplicationException {

        List<InvoiceDTO1> invoiceDTO1List = invoice1Service.findAllPurchaseInvoiceByCompanyId_SavedStatus(2);


        model.addAttribute("invoiceList",invoiceDTO1List);


        return "invoice/review-purchase";
    }

    @GetMapping("/review-sales")
    public String savedSalesInvoice(Model model) throws AccountingApplicationException {

        List<InvoiceDTO1> invoiceDTO1List = invoice1Service.findAllSalesInvoiceByCompanyId_SavedStatus(2);


        model.addAttribute("invoiceList",invoiceDTO1List);


        return "invoice/review-sales";
    }



    @GetMapping("/create-sales")
    public String getSalesInvoiceObject(Model model){

        SalesInvoiceDTO salesInvoiceDTO = new SalesInvoiceDTO();
        List<InvoiceDTO1> salesInvoiceNoList = invoice1Service.findAllSalesInvoiceByCompanyId_NoSavedStatus(2);
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);
        InvoiceDTO1 invoiceDTO1 = new InvoiceDTO1();

        model.addAttribute("salesInvoiceNoList",salesInvoiceNoList);
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("salesInvoiceDTO",salesInvoiceDTO);
        model.addAttribute("selectedInvoice",invoiceDTO1);

        return "invoice/sales-invoice";
    }

    public static int soldQTY;
    @PostMapping("/add-item-sales")
    public String addItemSales(@ModelAttribute("salesInvoiceDTO") SalesInvoiceDTO salesInvoiceDTO, Model model){

        String invoiceNumber = salesInvoiceDTO.getInvoiceNumber();
        String productDTO = salesInvoiceDTO.getProductNameDTO();
        Integer price = salesInvoiceDTO.getPrice();
        Integer qty = salesInvoiceDTO.getQty();

        InvoiceDTO1 invoiceDTO1 = null;
        List<ProductDTO> productDTOList = null;
        try {
            invoiceDTO1 = invoice1Service.findInvoice(invoiceNumber);
            if(invoiceDTO1.getTotalQTY()==0)
                soldQTY=0;
            productDTOList = profitService.updateInventoryByFIFO(invoiceNumber,productDTO,qty,price);
        } catch (AccountingApplicationException e) {
            model.addAttribute("errorMessage",e.getMessage());
        }

        SalesInvoiceDTO salesInvoiceDTO1 = new SalesInvoiceDTO();
        salesInvoiceDTO1.setInvoiceNumber(invoiceNumber);

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);

        soldQTY += qty;

        model.addAttribute("salesInvoiceNoList",salesInvoiceDTO.getInvoiceNumber());
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("salesInvoiceDTO",salesInvoiceDTO1);
        model.addAttribute("productDTOList",productDTOList);
        model.addAttribute("salesInvoice",invoiceDTO1);
        model.addAttribute("qty",soldQTY);

        return "invoice/add-item-sales";
    }



    @GetMapping("/add-item-sales/{invoiceNo}")
    public String addItemSales2(@PathVariable("invoiceNo") String invoiceNumber, Model model) throws AccountingApplicationException {

     //   String invoiceNumber = salesInvoiceDTO.getInvoiceNumber();
        String productDTO = null;
        Integer price =0;
        Integer qty=0;

        InvoiceDTO1 invoiceDTO1 = invoiceDTO1 = invoice1Service.findInvoice(invoiceNumber);;

        SalesInvoiceDTO salesInvoiceDTO1 = new SalesInvoiceDTO();
        salesInvoiceDTO1.setInvoiceNumber(invoiceNumber);

        List<ProductDTO> productList = invoiceDTO1.getProductList();
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(2);

        model.addAttribute("salesInvoiceNoList",invoiceNumber);
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("salesInvoiceDTO",salesInvoiceDTO1);
        model.addAttribute("productDTOList",productList);
        model.addAttribute("salesInvoice",invoiceDTO1);


        return "invoice/add-item-sales";
    }



    @GetMapping("/cancel-sales-invoice")
    public String cancelSalesInvoice(@Param("invoiceNo") String invoiceNo) throws AccountingApplicationException {

        invoice1Service.cancelSalesInvoice(invoiceNo);

        return "redirect:/invoice/review-sales";
    }




}
