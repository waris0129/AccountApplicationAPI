package com.account.repository;

import com.account.entity.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductNameRepository extends JpaRepository<ProductName,Integer> {

    Optional<ProductName> findByProductName(String productName);

    @Query("select p from ProductName p where p.company.deleted=false and p.category.enabled=true")
    List<ProductName> getAllProductNameDTOByCompany(Integer companyId);

}
