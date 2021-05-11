package com.account.controller;

import com.account.dto.*;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.*;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoice")
@PreAuthorize("hasAnyAuthority({'Employee'})")
public class InvoiceController {

    @Autowired
    private Invoice1Service invoice1Service;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ProductNameService productNameService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProfitService profitService;

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;


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

    private CompanyDTO getCurrentContextCompany() throws CompanyNotFoundException {
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

        CompanyDTO companyDTO= companyService.findById(id);

        return companyDTO;
    }





    @GetMapping("/create-invoice")
    public InvoiceDTO1 getInvoiceObject(Model model) throws CompanyNotFoundException {

        InvoiceDTO1 invoiceDTO1 = new InvoiceDTO1();
        invoiceDTO1.setCompany(getCurrentContextCompany());

        return invoiceDTO1;
    }




    @PostMapping("/create-invoice")
    public ResponseEntity<ResponseWrapper> createInvoice(@RequestParam String vendorName, @RequestParam String invoiceType) throws AccountingApplicationException, UserNotFoundInSystem, CompanyNotFoundException {

        InvoiceDTO1 invoiceDTO = invoice1Service.createNewInvoiceTemplate(vendorName,invoiceType);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Invoice is created").data(invoiceDTO).build());
    }




    @GetMapping("/delete-invoice/{invoiceNo}")
    public ResponseEntity<ResponseWrapper> deleteInvoice(@PathVariable("invoiceNo") String invoiceNo) throws AccountingApplicationException {

        invoice1Service.cancelInvoice(invoiceNo);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Invoice: "+invoiceNo+" is deleted").build());
    }




    @GetMapping("/create-purchase")
    public String getPurchaseInvoiceObject(Model model){

        PurchaseInvoiceDTO purchaseInvoiceDTO = new PurchaseInvoiceDTO();
        List<InvoiceDTO1> purchaseInvoiceNoList = invoice1Service.findAllPurchaseInvoiceByCompanyId_NoSavedStatus(getLoginCompanyId());
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());
        InvoiceDTO1 invoiceDTO1 = new InvoiceDTO1();

        model.addAttribute("purchaseInvoiceNoList",purchaseInvoiceNoList);
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("purchaseInvoiceDTO",purchaseInvoiceDTO);
        model.addAttribute("selectedInvoice",invoiceDTO1);

        return "invoice/purchase-invoice";
    }

    @GetMapping("/review-invoice")
    public ResponseEntity<ResponseWrapper> reviewInvoice(@RequestParam String invoiceNo,Model model) throws AccountingApplicationException {

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNo);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Invoice is found").data(invoiceDTO1).build());
    }


    @PostMapping("/add-item-purchase")
    public ResponseEntity<ResponseWrapper> AddItemPurchase(@RequestBody PurchaseInvoiceDTO purchaseInvoiceDTO,Model model) throws CompanyNotFoundException, AccountingApplicationException {

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

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Item is added").data(invoiceDTO1).build());
    }



    private static String invoiceNumberFromOutside = null;
    @GetMapping("/delete-add-item")
    public ResponseEntity<ResponseWrapper> deleteAddItem(@RequestParam String inventoryName, @RequestParam String invoiceNo, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        productService.deleteProduct(invoiceNo,inventoryName);

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNo);

        PurchaseInvoiceDTO purchaseInvoiceDTO = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO.setInvoiceNumber(invoiceNo);

        invoiceNumberFromOutside = invoiceNo;

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Item is added").data(invoiceDTO1).build());
    }

    @GetMapping("/add-item-purchase")
    public String getAddItemPurchase(Model model) throws CompanyNotFoundException, AccountingApplicationException {

        InvoiceDTO1 invoiceDTO1 = invoice1Service.findInvoice(invoiceNumberFromOutside);

        PurchaseInvoiceDTO purchaseInvoiceDTO2 = new PurchaseInvoiceDTO();
        purchaseInvoiceDTO2.setInvoiceNumber(invoiceNumberFromOutside);

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());
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

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());
        List<ProductDTO> productList = invoiceDTO1.getProductList();

        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("purchaseInvoiceDTO",purchaseInvoiceDTO2);
        model.addAttribute("selectInvoice",invoiceDTO1);
        model.addAttribute("productList",productList);

        return "invoice/add-item-purchase";
    }


    @PreAuthorize("hasAnyAuthority({'Manager'})")
    @GetMapping("/update-invoice-status")
    public ResponseEntity<ResponseWrapper> updateInvoiceStatus(@RequestParam String invoiceNo, @RequestParam String status) throws AccountingApplicationException {

        InvoiceDTO1 invoiceDTO1 = invoice1Service.updateInvoiceStatus(invoiceNo,status);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Invoice: "+invoiceNo+" Updated: "+status).data(invoiceDTO1).build());
    }


    @GetMapping("/review-purchase")
    public String savedPurchaseInvoice(Model model) throws AccountingApplicationException {

        List<InvoiceDTO1> invoiceDTO1List = invoice1Service.findAllPurchaseInvoiceByCompanyId_SavedStatus(getLoginCompanyId());


        model.addAttribute("invoiceList",invoiceDTO1List);


        return "invoice/review-purchase";
    }

    @GetMapping("/review-sales")
    public String savedSalesInvoice(Model model) throws AccountingApplicationException {

        List<InvoiceDTO1> invoiceDTO1List = invoice1Service.findAllSalesInvoiceByCompanyId_SavedStatus(getLoginCompanyId());


        model.addAttribute("invoiceList",invoiceDTO1List);


        return "invoice/review-sales";
    }



    @GetMapping("/create-sales")
    public String getSalesInvoiceObject(Model model){

        SalesInvoiceDTO salesInvoiceDTO = new SalesInvoiceDTO();
        List<InvoiceDTO1> salesInvoiceNoList = invoice1Service.findAllSalesInvoiceByCompanyId_NoSavedStatus(getLoginCompanyId());
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());
        InvoiceDTO1 invoiceDTO1 = new InvoiceDTO1();

        model.addAttribute("salesInvoiceNoList",salesInvoiceNoList);
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("salesInvoiceDTO",salesInvoiceDTO);
        model.addAttribute("selectedInvoice",invoiceDTO1);

        return "invoice/sales-invoice";
    }

    public static int soldQTY;
    @PostMapping("/add-item-sales")
    public ResponseEntity<ResponseWrapper> addItemSales(@RequestBody SalesInvoiceDTO salesInvoiceDTO, Model model) throws AccountingApplicationException {

        String invoiceNumber = salesInvoiceDTO.getInvoiceNumber();
        String productDTO = salesInvoiceDTO.getProductNameDTO();
        Integer price = salesInvoiceDTO.getPrice();
        Integer qty = salesInvoiceDTO.getQty();

        InvoiceDTO1 invoiceDTO1 = invoiceDTO1 = invoice1Service.findInvoice(invoiceNumber);
        List<ProductDTO> productDTOList = profitService.updateInventoryByFIFO(invoiceNumber,productDTO,qty,price);
            if(invoiceDTO1.getTotalQTY()==0)
                soldQTY=0;

            invoiceDTO1.setProductList(productDTOList);


        SalesInvoiceDTO salesInvoiceDTO1 = new SalesInvoiceDTO();
        salesInvoiceDTO1.setInvoiceNumber(invoiceNumber);

        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());

        soldQTY += qty;

        model.addAttribute("salesInvoiceNoList",salesInvoiceDTO.getInvoiceNumber());
        model.addAttribute("productNameList",productNameDTOList);
        model.addAttribute("salesInvoiceDTO",salesInvoiceDTO1);
        model.addAttribute("productDTOList",productDTOList);
        model.addAttribute("salesInvoice",invoiceDTO1);
        model.addAttribute("qty",soldQTY);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Sales item is added").data(invoiceDTO1).build());
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
        List<ProductNameDTO> productNameDTOList = productNameService.getAllProductNameDTOByCompany(getLoginCompanyId());

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
