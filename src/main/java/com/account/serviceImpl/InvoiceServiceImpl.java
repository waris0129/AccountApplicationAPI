package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.InvoiceDTO;
import com.account.dto.VendorDTO;
import com.account.entity.Invoice;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import com.account.enums.RegistrationType;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.InvoiceRepository;
import com.account.service.CompanyService;
import com.account.service.InvoiceNumberService;
import com.account.service.InvoiceService;
import com.account.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceNumberService invoiceNumberService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public InvoiceDTO createNewInvoiceTemplate(String vendorName, String invoiceType) throws UserNotFoundInSystem {

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        String invoiceNo = invoiceNumberService.createInvoiceNumber();
        VendorDTO vendorDTO = vendorService.get(vendorName);

        invoiceDTO.setInvoiceNo(invoiceNo);
        invoiceDTO.setCompany(companyService.findById(1));
        invoiceDTO.setVendor(vendorDTO);
        invoiceDTO.setInvoiceStatus(InvoiceStatus.PENDING);
        invoiceDTO.setInvoiceType(InvoiceType.valueOf(invoiceType.toUpperCase()));
        invoiceDTO.setEnabled(true);
        invoiceDTO.setInvoiceDate(LocalDate.now());

        Invoice invoice = mapperUtility.convert(invoiceDTO,new Invoice());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceDTO savedDTO = mapperUtility.convert(savedInvoice, new InvoiceDTO());

        return savedDTO;
    }

    @Override
    public InvoiceDTO updateInvoiceStatus(String invoiceNumber,InvoiceDTO invoiceDTO) throws AccountingApplicationException {

        Optional<Invoice> foundInvoice = invoiceRepository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice invoice = foundInvoice.get();

        invoice.setInvoiceStatus(invoiceDTO.getInvoiceStatus());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceDTO dto = mapperUtility.convert(savedInvoice,new InvoiceDTO());

        return dto;
    }

    @Override
    public InvoiceDTO updateInvoiceStatus(String invoiceNumber, String status) throws AccountingApplicationException {

        Optional<Invoice> foundInvoice = invoiceRepository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice invoice = foundInvoice.get();

        invoice.setInvoiceStatus(InvoiceStatus.valueOf(status.toUpperCase()));

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceDTO dto = mapperUtility.convert(savedInvoice,new InvoiceDTO());

        return dto;
    }

    @Override
    public InvoiceDTO cancelInvoice(String invoiceNumber) throws AccountingApplicationException {

        Optional<Invoice> foundInvoice = invoiceRepository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice invoice = foundInvoice.get();

        invoice.setInvoiceStatus(InvoiceStatus.CANCEL);
        invoice.setEnabled(false);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceDTO invoiceDTO = mapperUtility.convert(savedInvoice,new InvoiceDTO());

        return invoiceDTO;
    }

    @Override
    public InvoiceDTO findInvoice(String invoiceNumber) throws AccountingApplicationException {

        Optional<Invoice> foundInvoice = invoiceRepository.findByInvoiceNo(invoiceNumber);

        if(!foundInvoice.isPresent())
            throw new AccountingApplicationException("Invoice not found in system");

        Invoice invoice = foundInvoice.get();

        InvoiceDTO invoiceDTO = mapperUtility.convert(invoice, new InvoiceDTO());

        return invoiceDTO;
    }
}
