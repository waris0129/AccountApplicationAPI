package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.List;

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
    private List<Product> products;
    private BigDecimal unitPrice;
    private Integer qty;


}
