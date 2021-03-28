package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.*;
import com.account.entity.*;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.CompanyRepository;
import com.account.repository.Invoice1Repository;
import com.account.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@Service
public class Invoice1ServiceImpl implements Invoice1Service {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private Invoice1Repository invoice1Repository;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private ProductNameService productNameService;
    @Autowired
    private ProductService productService;


    private static InvoiceDTO1 invoiceDTO1;
    private Integer year = LocalDate.now().getYear();
    private static int number = 0;


    public Invoice1ServiceImpl() {
        this.invoiceDTO1 = new InvoiceDTO1();
    }

    @Override
    public String createInvoiceNumber() {

        invoiceDTO1.setEnabled(true);
        CompanyDTO companyDTO = companyService.findById(1);
        invoiceDTO1.setCompany(companyDTO);
        invoiceDTO1.setYear(year);

        checkYear(year);

        number = ++number;

        String createNumber = companyDTO.getTitle().toUpperCase().substring(0,4).trim()+"-"+
                year+"_00"+ number;

        invoiceDTO1.setInvoiceNo(createNumber);

        Invoice1 invoice1 = mapperUtility.convert(invoiceDTO1, new Invoice1());

        Invoice1 invoice11 = invoice1Repository.save(invoice1);

        return invoice11.getInvoiceNo();
    }

    private void checkYear(Integer year){
        // check database
        List<Integer> yearList = invoice1Repository.getAllYearList(1);

        if(!yearList.contains(year))
            number = 0;


    }

    @Override
    public InvoiceDTO1 createNewInvoiceTemplate(String vendorName, String invoiceType) throws AccountingApplicationException, UserNotFoundInSystem {

        Optional<Invoice1> foundInvoice = invoice1Repository.findByInvoiceNo(createInvoiceNumber());

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");


        Invoice1 invoice1 = foundInvoice.get();


        VendorDTO vendorDTO = vendorService.get(vendorName);

        invoice1.setVendor(mapperUtility.convert(vendorDTO,new Vendor()));
        invoice1.setInvoiceStatus(InvoiceStatus.PENDING);
        invoice1.setInvoiceType(InvoiceType.valueOf(invoiceType.toUpperCase()));
        invoice1.setLocalDate(LocalDate.now());
        invoice1.setEnabled(true);

        Invoice1 savedInvoice = invoice1Repository.save(invoice1);

        InvoiceDTO1 savedDTO = mapperUtility.convert(savedInvoice, new InvoiceDTO1());

        return savedDTO;
    }

    @Override
    public InvoiceDTO1 updateInvoiceStatus(String invoiceNumber, String status) throws AccountingApplicationException {
        Optional<Invoice1> foundInvoice = invoice1Repository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice1 invoice = foundInvoice.get();

        invoice.setInvoiceStatus(InvoiceStatus.valueOf(status.toUpperCase()));

        Invoice1 savedInvoice = invoice1Repository.save(invoice);

        InvoiceDTO1 dto = mapperUtility.convert(savedInvoice,new InvoiceDTO1());

        return dto;
    }

    @Override
    public InvoiceDTO1 cancelInvoice(String invoiceNumber) throws AccountingApplicationException {
        Optional<Invoice1> foundInvoice = invoice1Repository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice1 invoice = foundInvoice.get();

        invoice.setInvoiceStatus(InvoiceStatus.CANCEL);
        invoice.setEnabled(false);

        Invoice1 savedInvoice = invoice1Repository.save(invoice);

        InvoiceDTO1 invoiceDTO = mapperUtility.convert(savedInvoice,new InvoiceDTO1());

        return invoiceDTO;
    }

    @Override
    public InvoiceDTO1 findInvoice(String invoiceNumber) throws AccountingApplicationException {
        Optional<Invoice1> foundInvoice = invoice1Repository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice1 invoice = foundInvoice.get();

        InvoiceDTO1 invoiceDTO = mapperUtility.convert(invoice, new InvoiceDTO1());

        return invoiceDTO;
    }


    @Override
    public InvoiceDTO1 addProductItem(String invoiceNumber, String productName, Integer price, Integer qty) throws CompanyNotFoundException, AccountingApplicationException {
        Optional<Invoice1> foundInvoiceProduct = invoice1Repository.findByInvoiceNumber(invoiceNumber);
        if(!foundInvoiceProduct.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice1 invoiceProduct = foundInvoiceProduct.get();

        ProductNameDTO productNameDTO = productNameService.findProductNameDTO(productName);

        ProductDTO productDTO = new ProductDTO();

        productDTO.setInventoryNo(invoiceProduct.getInvoiceNo());
        productDTO.setName(productNameDTO);
        productDTO.setQty(qty);
        productDTO.setPrice(price);
        productDTO.setCategory(productNameDTO.getCategory());
        productDTO.setCompany(productNameDTO.getCompany());

        ProductDTO savedProduct = productService.saveProduct(productDTO);
        Product product = mapperUtility.convert(savedProduct,new Product());

        invoiceProduct.getProductList().add(product);

        Invoice1 savedInvoiceProduct = invoice1Repository.save(invoiceProduct);

        InvoiceDTO1 savedInvoiceProductDTO = mapperUtility.convert(savedInvoiceProduct, new InvoiceDTO1());

        return savedInvoiceProductDTO;
    }

}
