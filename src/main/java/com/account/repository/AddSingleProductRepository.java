package com.account.repository;

import com.account.entity.AddSingleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddSingleProductRepository extends JpaRepository<AddSingleProduct,Integer> {
}
