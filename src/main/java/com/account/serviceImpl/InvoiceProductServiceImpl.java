package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.*;
import com.account.entity.InvoiceProduct;
import com.account.entity.SingleInvoiceProduct;
import com.account.enums.InvoiceStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.InvoiceProductRepository;
import com.account.repository.InvoiceRepository;
import com.account.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    @Autowired
    private InvoiceNumberService invoiceNumberService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private InvoiceRepository invoiceRepository;


    @Override
    public InvoiceProductDTO createNewInvoiceProductTemplate(String vendorName) throws UserNotFoundInSystem {




        return null;
    }

    @Override
    public InvoiceProductDTO addProductItem(String invoiceNumber,SingleInvoiceProductDTO singleInvoiceProductDTO) throws CompanyNotFoundException {

//        Optional<InvoiceProduct> foundEntity = invoiceProductRepository.findByInvoiceNumber(invoiceNumber);
//
//        InvoiceProductDTO dto = null;
//
//        if(invoiceProductDTO.getInvoice().getInvoiceNo().equalsIgnoreCase(invoiceNumber))
//            dto = invoiceProductDTO;
//        else if (foundEntity.isPresent())
//            dto = mapperUtility.convert(foundEntity.get(),new InvoiceProductDTO());
//
//        dto.getSingleInvoiceProductDTO().add(singleInvoiceProductDTO);
//
//        InvoiceProduct invoiceProduct = mapperUtility.convert(dto,new InvoiceProduct());
//
//        InvoiceProduct savedInvoiceProduct = invoiceProductRepository.save(invoiceProduct);
//
//        InvoiceProductDTO savedDTO = mapperUtility.convert(savedInvoiceProduct,new InvoiceProductDTO());


        return null;
    }



    @Override
    public InvoiceProductDTO getInvoice(String invoiceNumber) throws AccountingApplicationException {
        InvoiceProductDTO dto = null;
        Optional<InvoiceProduct> foundEntity = invoiceProductRepository.findByInvoiceNumber(invoiceNumber);

        if (foundEntity.isPresent())
            dto = mapperUtility.convert(foundEntity.get(),new InvoiceProductDTO());
        else
            throw new AccountingApplicationException("Invoice not found in system");


        return null;
    }









}
