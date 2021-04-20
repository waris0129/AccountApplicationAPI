package com.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesInvoiceDTO {

    private String invoiceNumber;
    private String productNameDTO;
    private Integer qty;
    private Integer price;



}
