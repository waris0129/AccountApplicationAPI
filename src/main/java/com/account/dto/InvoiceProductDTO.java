package com.account.dto;

import com.account.entity.AddSingleProduct;
import com.account.entity.Invoice;
import com.account.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDTO {

    private Integer id;
    private InvoiceDTO invoice;
    private List<AddSingleProductDTO> productList = new ArrayList<>();
}
