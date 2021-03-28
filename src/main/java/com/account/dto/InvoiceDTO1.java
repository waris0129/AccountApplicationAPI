package com.account.dto;

import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO1 {

    private Integer id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate localDate;
    private VendorDTO vendor;
    private CompanyDTO company;
    private Boolean enabled;
    private Integer year;
    private List<ProductDTO> productList = new ArrayList<>();


}
