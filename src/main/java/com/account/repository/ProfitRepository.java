package com.account.repository;

import com.account.entity.Profit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitRepository extends JpaRepository<Profit,Integer> {
}
