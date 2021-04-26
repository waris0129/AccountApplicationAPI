package com.account.repository;

import com.account.dto.ProfitDTO;
import com.account.entity.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfitRepository extends JpaRepository<Profit,Integer> {

    @Query("SELECT p FROM Profit p WHERE p.salesInvoiceNo.company.id=?1 and p.salesInvoiceNo.invoiceStatus='COMPLETE'")
    List<Profit> getAllProfitByCompanyId(Integer companyId);

    @Query(value = "SELECT * FROM Profit as P WHERE P.sales_invoice_no_id=?1",nativeQuery = true)
    Profit findProfitByInvoiceId(Integer id);


}
