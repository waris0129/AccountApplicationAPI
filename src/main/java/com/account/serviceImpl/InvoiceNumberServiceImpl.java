package com.account.serviceImpl;

import com.account.entity.Company;
import com.account.entity.InvoiceNumber;
import com.account.repository.CompanyRepository;
import com.account.repository.InvoiceNumberRepository;
import com.account.service.InvoiceNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceNumberServiceImpl implements InvoiceNumberService {

    // apply security in this class to get Company object

    private InvoiceNumber invoiceNumber;
    private Company company;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private InvoiceNumberRepository invoiceNumberRepository;

    public InvoiceNumberServiceImpl() {
        this.invoiceNumber = new InvoiceNumber();
    }

    private static Integer number;
    private static Integer id=0;
    private Integer year = LocalDate.now().getYear();




    @Override
    public String createInvoiceNumber() {
        company = companyRepository.findById(1).get();
        invoiceNumber.setId(++id);
        invoiceNumber.setCompany(company);
        invoiceNumber.setYear(year);
        checkYear();
        number = ++number;
        invoiceNumber.setInvoiceNumber(number);

        invoiceNumberRepository.save(invoiceNumber);

        String createNumber = "#"+company.getTitle().toUpperCase().substring(0,4).trim()+"-"+
                              year+"-00"+number;


        return createNumber;
    }

    private void checkYear(){
        // check database
        Optional<List<Integer>> yearList = invoiceNumberRepository.getAllYearList(1);// hard coding it

        if(yearList.isPresent()){
            if(!yearList.get().contains(year)){
                number = 0;
            }
        }

    }
}
