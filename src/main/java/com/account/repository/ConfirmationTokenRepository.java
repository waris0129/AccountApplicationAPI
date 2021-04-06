package com.account.repository;


import com.account.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Query("SELECT p FROM ConfirmationToken p where p.user.id = ?1")
    ConfirmationToken findByUserId(long id);

}
