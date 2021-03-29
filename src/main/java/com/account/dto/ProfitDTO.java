package com.account.dto;

import com.account.entity.Invoice1;
import com.account.entity.ProductName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfitDTO {

    private Integer id;
    private InvoiceDTO1 salesInvoiceNo;
    private Set<ProductNameDTO> productName = new HashSet<>();
    private Integer soldQty;
    private BigDecimal totalCost;
    private BigDecimal totalSales;
    private BigDecimal profit;


}
