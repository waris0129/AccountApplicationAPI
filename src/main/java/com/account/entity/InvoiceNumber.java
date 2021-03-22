package com.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class InvoiceNumber{

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Integer year;
    private Integer invoiceNumber;
}
