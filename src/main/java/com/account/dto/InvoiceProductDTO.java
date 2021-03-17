package com.account.dto;

import com.account.entity.Invoice;
import com.account.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDTO {

    private Invoice invoice;
    private List<Product> products;
    private BigDecimal unitPrice;
    private Integer qty;


}
