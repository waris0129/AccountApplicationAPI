package com.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInvoiceDTO {

    private String invoiceNumber;
    private String productNameDTO;
    private Integer qty;
    private Integer price;



}
