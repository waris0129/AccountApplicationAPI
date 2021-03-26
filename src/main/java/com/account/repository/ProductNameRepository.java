package com.account.repository;

import com.account.entity.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductNameRepository extends JpaRepository<ProductName,Integer> {

    Optional<ProductName> findByProductName(String productName);

}
