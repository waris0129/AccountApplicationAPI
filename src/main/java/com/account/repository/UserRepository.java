package com.account.repository;

import com.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT p FROM User p WHERE p.role.role = ?1")
    List<User> getAllByRole(String role);


    Optional<User>  findUserByEmail(String email);

}
