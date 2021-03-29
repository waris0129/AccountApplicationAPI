package com.account.entity;

import com.account.enums.Unit;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "enabled=true")
public class Product extends BaseEntity{


    private String inventoryNo;

    @ManyToOne()
    @JoinColumn(name = "product_register_id")
    private ProductName name;
    private Integer qty;
    private Integer availableStock;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private Boolean enabled;


}
