package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity {

    private String invoiceNo;
    private String invoiceStatus;
    @OneToOne
    private InvoiceNumber invoiceNumberId;
    private String invoiceType;
    private LocalDate invoiceDate;
    @OneToOne
    private SPTable spTableId;
    @OneToOne
    private Company companyId;
    private Boolean enabled;

}
