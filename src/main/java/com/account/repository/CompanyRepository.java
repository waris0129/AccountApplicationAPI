package com.account.repository;

import com.account.entity.Company;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {

        Optional<Company> findByTitle(String title);
}
