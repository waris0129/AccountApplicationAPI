package com.account.repository;

import com.account.entity.Company;
import com.account.entity.Vendor;
import com.account.enums.VendorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {

    Vendor getByCompanyName(String name);

    @Query("SELECT p from Vendor p where p.status = ?1 and p.company.deleted=false")
    List<Vendor> getAllVendorByStatus(VendorStatus status);


    @Query("SELECT p from Vendor p where p.status = 'ACTIVE' and p.company.id=?1 and p.company.deleted=false")
    List<Vendor> getAllActiveVendorByCompany(Integer companyId);

    @Query("SELECT p from Vendor p where p.company.deleted=false ")
    List<Vendor> getAllVendorList();

}
