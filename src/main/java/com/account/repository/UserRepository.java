package com.account.repository;

import com.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> getAllByRole(String role);
    Optional<User>  findUserByEmail(String email);

}
