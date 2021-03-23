package com.account.repository;

import com.account.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct,Integer> {

    @Query("SELECT p FROM InvoiceProduct p where p.invoice.invoiceNo=?1")
    Optional<InvoiceProduct> findByInvoiceNumber(String invoiceNumber);


}
