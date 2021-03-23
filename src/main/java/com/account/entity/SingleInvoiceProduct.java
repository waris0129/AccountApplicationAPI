package com.account.entity;

import com.account.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingleInvoiceProduct extends BaseEntity{

    @OneToOne
    private Product product;
    private BigDecimal unitPrice;
    private Integer qty;

}
