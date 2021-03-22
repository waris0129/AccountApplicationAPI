package com.account.dto;


import com.account.entity.Company;
import com.account.entity.InvoiceNumber;
import com.account.entity.Vendor;
import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO{

    private Integer id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate invoiceDate;
    private VendorDTO vendor;
    private CompanyDTO company;
    private Boolean enabled;

}
