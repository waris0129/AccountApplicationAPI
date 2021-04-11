package com.account.repository;

import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.enums.CompanyStatus;
import com.account.enums.UserRole;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT p FROM User p where p.role.role=?1 and p.company.deleted=false")
    List<User> getAllByRole(UserRole role);


    Optional<User>  findUserByEmail(String email);

    @Query("SELECT p FROM User p where p.role.role=?1 and p.company.deleted=false and p.company.status=?2")
    List<User> getAllByRoleAndStatus(UserRole role, CompanyStatus status);

    @Query("SELECT p FROM User p where p.company.id=?1 and p.company.deleted=false")
    List<User> getUserListByCompany(Integer companyId);

}
