package com.account.repository;

import com.account.entity.InvoiceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceNumberRepository extends JpaRepository<InvoiceNumber,Integer> {

    @Query("SELECT distinct year from InvoiceNumber where company.id = ?1")
    Optional<List<Integer>> getAllYearList(Integer companyId);

}
