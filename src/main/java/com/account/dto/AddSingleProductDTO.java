package com.account.dto;

import com.account.entity.InvoiceProduct;
import com.account.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSingleProductDTO {

    private Integer id;
    private ProductDTO product;
    private InvoiceProductDTO invoiceProduct;
    private BigDecimal price;
    private Integer qty;


}
