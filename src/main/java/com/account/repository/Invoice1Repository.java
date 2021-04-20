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

    @Query("SELECT p FROM Invoice1 p where p.company.id=?1")
    List<Invoice1> findAllInvoiceByCompanyId(Integer companyId);


    @Query("SELECT p FROM Invoice1 p where p.company.id=?1 and p.invoiceType='SALES'")
    List<Invoice1> findAllSalesInvoiceByCompanyId(Integer companyId);

    @Query("SELECT p FROM Invoice1 p where p.company.id=?1 and p.invoiceType='PURCHASE'")
    List<Invoice1> findAllPurchaseInvoiceByCompanyId(Integer companyId);

    @Query("SELECT p FROM Invoice1 p where p.company.id=?1 and p.invoiceType='PURCHASE' and p.invoiceStatus='PENDING'")
    List<Invoice1> findAllPurchaseInvoiceByCompanyId_NoSavedStatus(Integer companyId);


    @Query("SELECT p FROM Invoice1 p where p.company.id=?1 and p.invoiceType='PURCHASE' and (p.invoiceStatus='REVIEW' or p.invoiceStatus='APPROVED')")
    List<Invoice1> findAllPurchaseInvoiceByCompanyId_SavedStatus(Integer companyId);

    @Query("SELECT p FROM Invoice1 p where p.company.id=?1 and p.invoiceStatus='PENDING'")
    List<Invoice1> findAllInvoiceByCompanyId_NoSavedStatus(Integer companyId);

    @Query("SELECT p FROM Invoice1 p where p.company.id=?1 and p.invoiceType='SALES' and p.invoiceStatus='PENDING'")
    List<Invoice1> findAllSalesInvoiceByCompanyId_NoSavedStatus(Integer companyId);

}
