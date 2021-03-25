package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

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
    private List<AddSingleProduct> productList = new ArrayList<>();
}
