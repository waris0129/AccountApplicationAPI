package com.account.entity;

import com.account.dto.SingleInvoiceProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProduct extends BaseEntity{


    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    @OneToMany
    @JoinTable(name = "product_list")
    private List<SingleInvoiceProduct> singleInvoiceProduct;

}
