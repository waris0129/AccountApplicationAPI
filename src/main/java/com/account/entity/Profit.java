package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Profit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    private Integer availableStock;
    private Integer soldStock;
    private Boolean sold;
    private Integer cost;
    private Integer sales;
    private BigDecimal totalCost;
    private BigDecimal totalSales;
    private BigDecimal profit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice1 invoice1;



}
