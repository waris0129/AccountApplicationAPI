package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProduct extends BaseEntity{

    @OneToOne
    private Invoice invoiceId;
    @OneToOne
    private Product productId;
    private BigDecimal unitPrice;
    private Integer qty;


}
