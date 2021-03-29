package com.account.repository;

import com.account.dto.ProductDTO;
import com.account.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p where p.name.productName=?1 order by p.id")
    List<Product> findByName(String name);

    @Query("select p from Product p where p.inventoryNo=?1")
    Optional<Product> findByInventoryNo(String inventoryNo);



  //  List<Product> findProductListByProductNameAndAvailableStock(String productName, Integer availableStock);
}
