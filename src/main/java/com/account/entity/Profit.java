package com.account.entity;

import com.account.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profit extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    private Invoice1 salesInvoiceNo;

    @OneToMany()
    @JoinColumn(name = "profit_product")
    private Set<ProductName> productName = new HashSet<>();

    private Integer soldQty;
    private BigDecimal totalCost;
    private BigDecimal totalSales;
    private BigDecimal profit;











}
