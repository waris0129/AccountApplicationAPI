package com.account.serviceImpl;

import com.account.dto.InvoiceDTO;
import com.account.dto.InvoiceProductDTO;
import com.account.dto.ProductDTO;
import com.account.dto.VendorDTO;
import com.account.entity.InvoiceNumber;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.repository.CompanyRepository;
import com.account.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private InvoiceProductDTO invoiceProductDTO = new InvoiceProductDTO();

    @Autowired
    private InvoiceNumberService invoiceNumberService;
    @Autowired
    private CompanyService companyService;


    @Autowired
    private VendorService vendorService;


    @Override
    public InvoiceProductDTO createInvoiceView(){

        String invoiceNo = invoiceNumberService.createInvoiceNumber();

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNo(invoiceNo);
        invoiceDTO.setCompany(companyService.findById(1));

        List<VendorDTO> vendorDTO = vendorService.getAllVendorList();

        invoiceProductDTO.setInvoice(invoiceDTO);
        invoiceProductDTO.setProducts(new ArrayList<ProductDTO>());



        return invoiceProductDTO;
    }

    @Override
    public InvoiceProductDTO addProductItem(ProductDTO productDTO) throws CompanyNotFoundException {



        return null;
    }
}
