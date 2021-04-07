package com.account.repository;

import com.account.dto.CompanyDTO;
import com.account.entity.Company;
import com.account.enums.CompanyStatus;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

        @Query("select p from Company p where p.title=?1 and p.status='ACTIVE'")
        Optional<Company> findByTitle(String title);

        List<Company> findAllByStatus(CompanyStatus status);

}
