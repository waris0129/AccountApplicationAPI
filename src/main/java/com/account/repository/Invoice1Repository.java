package com.account.repository;

import com.account.entity.Invoice1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Invoice1Repository extends JpaRepository<Invoice1,Integer> {

    Optional<Invoice1> findByInvoiceNo(String invoiceNumber);

    @Query("SELECT distinct year from Invoice1 where company.id = ?1")
    List<Integer> getAllYearList(Integer companyId);

    @Query("SELECT p FROM Invoice1 p where p.invoiceNo=?1")
    Optional<Invoice1> findByInvoiceNumber(String invoiceNo);



}
