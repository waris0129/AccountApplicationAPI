package com.account.repository;

import com.account.entity.Company;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

        @Query("select p from Company p where p.title=?1 and p.status='ACTIVE'")
        Optional<Company> findByTitle(String title);
}
