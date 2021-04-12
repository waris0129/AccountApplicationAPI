package com.account.repository;

import com.account.entity.Category;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findByCategory(String category);

    @Query("SELECT p FROM Category p where p.company.id=?1 and p.company.deleted=false")
    List<Category> getAllCategoriesByCompany(Integer companyId);

}
