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
import java.util.stream.Collectors;


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
    @Autowired
    private ProfitService profitService;


    private static InvoiceDTO1 invoiceDTO1;
    private Integer year = LocalDate.now().getYear();
    private static int number = 0;


    public Invoice1ServiceImpl() {
        this.invoiceDTO1 = new InvoiceDTO1();
    }

    @Override
    public String createInvoiceNumber(String invoiceType) throws CompanyNotFoundException {

        invoiceDTO1.setEnabled(true);
        CompanyDTO companyDTO = companyService.findById(2); // hard coding it
        invoiceDTO1.setCompany(companyDTO);
        invoiceDTO1.setYear(year);
        invoiceDTO1.setInvoiceType(InvoiceType.valueOf(invoiceType.toUpperCase()));

        checkYear(year);

        number = ++number;

        String createNumber = companyDTO.getTitle().toUpperCase().substring(0,4).trim()+"-"+
                year+"_"+invoiceType.toUpperCase()+"_00"+ number;

        invoiceDTO1.setInvoiceNo(createNumber);

        Invoice1 invoice1 = mapperUtility.convert(invoiceDTO1, new Invoice1());

        Invoice1 invoice11 = invoice1Repository.save(invoice1);

        return invoice11.getInvoiceNo();
    }

    private void checkYear(Integer year){
        // check database
        List<Integer> yearList = invoice1Repository.getAllYearList(2);

        if(!yearList.contains(year))
            number = 0;


    }

    @Override
    public InvoiceDTO1 createNewInvoiceTemplate(String vendorName, String invoiceType) throws AccountingApplicationException, UserNotFoundInSystem, CompanyNotFoundException {

        Optional<Invoice1> foundInvoice = invoice1Repository.findByInvoiceNo(createInvoiceNumber(invoiceType));

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");


        Invoice1 invoice1 = foundInvoice.get();


        VendorDTO vendorDTO = vendorService.get(vendorName);

        invoice1.setVendor(mapperUtility.convert(vendorDTO,new Vendor()));
        invoice1.setInvoiceStatus(InvoiceStatus.PENDING);
        invoice1.setLocalDate(LocalDate.now());
        invoice1.setEnabled(true);
        invoice1.setTotalPrice(0);
        invoice1.setTotalQTY(0);

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
    public InvoiceDTO1 updateInvoice(String invoiceNumber, InvoiceDTO1 invoiceDTO1) throws AccountingApplicationException {
        Optional<Invoice1> foundInvoice = invoice1Repository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice1 invoice = foundInvoice.get();

        Integer id = invoice.getId();

        invoiceDTO1.setId(id);

        Invoice1 invoice2 = mapperUtility.convert(invoiceDTO1,new Invoice1());

        Invoice1 savedInvoice = invoice1Repository.save(invoice2);

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
    public InvoiceDTO1 cancelSalesInvoice(String salesInvoiceNumber) throws AccountingApplicationException {


        Optional<Invoice1> foundInvoice = invoice1Repository.findByInvoiceNo(salesInvoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice1 invoice = foundInvoice.get();

        // update product available Stock
        profitService.updateInventoryByFIFO_SalesCancel(salesInvoiceNumber);

        invoice.setInvoiceStatus(InvoiceStatus.CANCEL);
        invoice.setEnabled(false);

        Invoice1 savedInvoice = invoice1Repository.save(invoice);

        InvoiceDTO1 invoiceDTO = mapperUtility.convert(savedInvoice,new InvoiceDTO1());




        return null;
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
    public Integer calculateTotalCost(String invoiceNumber) {

        Invoice1 invoice1 = invoice1Repository.findByInvoiceNo(invoiceNumber).get();

        Integer price = invoice1.getProductList().stream().filter(p->p.getEnabled().equals(true)).mapToInt(p->p.getPrice()*p.getQty()).sum();

        invoice1.setTotalPrice(price);

        return invoice1.getTotalPrice();
    }


    @Override
    public Invoice1 updateTotalCost(String invoiceNumber) {

        Invoice1 invoice1 = invoice1Repository.findByInvoiceNo(invoiceNumber).get();

        invoice1.setTotalPrice(invoice1.getProductList().stream().filter(p->p.getEnabled().equals(true)).mapToInt(p->p.getPrice()*p.getQty()).sum());

        return invoice1Repository.save(invoice1);
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
        productDTO.setAvailableStock(qty);
        productDTO.setPrice(price);
        productDTO.setCategory(productNameDTO.getCategory());
        productDTO.setCompany(productNameDTO.getCompany());

        ProductDTO savedProduct = productService.saveProduct(productDTO);
        Product product = mapperUtility.convert(savedProduct,new Product());

        invoiceProduct.getProductList().add(product);

        invoiceProduct.setTotalPrice(calculateTotalCost(invoiceNumber));

        Invoice1 savedInvoiceProduct = invoice1Repository.save(invoiceProduct);

        InvoiceDTO1 savedInvoiceProductDTO = mapperUtility.convert(savedInvoiceProduct, new InvoiceDTO1());

        return savedInvoiceProductDTO;
    }


    @Override
    public List<InvoiceDTO1> findAllInvoiceByCompanyId(Integer companyId) {

        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllInvoiceByCompanyId(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO1> findAllSalesInvoiceByCompanyId(Integer companyId) {
        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllSalesInvoiceByCompanyId(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO1> findAllPurchaseInvoiceByCompanyId(Integer companyId) {
        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllPurchaseInvoiceByCompanyId(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO1> findAllPurchaseInvoiceByCompanyId_NoSavedStatus(Integer companyId) {
        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllPurchaseInvoiceByCompanyId_NoSavedStatus(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO1> findAllSalesInvoiceByCompanyId_NoSavedStatus(Integer companyId) {
        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllSalesInvoiceByCompanyId_NoSavedStatus(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }


    @Override
    public List<InvoiceDTO1> findAllInvoiceByCompanyId_NoSavedStatus(Integer companyId) {
        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllInvoiceByCompanyId_NoSavedStatus(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO1> findAllPurchaseInvoiceByCompanyId_SavedStatus(Integer companyId) {
        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllPurchaseInvoiceByCompanyId_SavedStatus(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }

    //findAllSalesInvoiceByCompanyId_SavedStatus
    @Override
    public List<InvoiceDTO1> findAllSalesInvoiceByCompanyId_SavedStatus(Integer companyId) {
        List<Invoice1> invoiceDTO1List = invoice1Repository.findAllSalesInvoiceByCompanyId_SavedStatus(companyId);

        return invoiceDTO1List.stream().map(p->mapperUtility.convert(p,new InvoiceDTO1())).collect(Collectors.toList());
    }

}
