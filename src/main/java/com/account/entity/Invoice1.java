package com.account.entity;


import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "enabled=true")
public class Invoice1 extends BaseEntity{

    private String invoiceNo;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    private LocalDate localDate;

    private Integer year;

    @OneToMany
    private List<Product> productList = new ArrayList<>();


    @OneToOne
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private Boolean enabled;

    private Integer totalPrice;
    private Integer totalQTY;

}
