package com.account.repository;

import com.account.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {

    Vendor getByCompanyName(String name);

    @Query("SELECT p from Vendor p where p.status = ?1")
    List<Vendor> getAllByStatus(String status);

}
