package com.account.entity;

import com.account.enums.InvoiceStatus;
import com.account.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity {

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String invoiceNo;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @OneToOne
    private InvoiceNumber invoiceNumberId;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    private LocalDate invoiceDate;

    @OneToOne
    private SPTable spTableId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private Boolean enabled;

}
