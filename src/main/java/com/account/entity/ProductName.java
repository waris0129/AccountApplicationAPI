package com.account.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "enabled=true")
public class ProductName extends BaseEntity{


    private Integer id;
    private String productName;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Company company;
    private Boolean enabled;

}
