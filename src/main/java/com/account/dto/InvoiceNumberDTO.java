package com.account.dto;

import com.account.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceNumberDTO {

    private Integer id;
    private Company company;
    private Integer year;
    private Integer invoiceNumber;
}
