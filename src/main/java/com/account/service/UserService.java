package com.account.service;

import com.account.dto.CompanyDTO;
import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.enums.CompanyStatus;
import com.account.enums.UserRole;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto) throws AccountingApplicationException;
    UserDto update(String email, UserDto userDto) throws UserNotFoundInSystem, AccountingApplicationException;
    UserDto update(UserDto userDto);
    UserDto getUserById(Integer id);
    UserDto getUser(String email) throws UserNotFoundInSystem, AccountingApplicationException;
    UserDto deleteUser(String email) throws UserNotFoundInSystem, AccountingApplicationException;
    List<UserDto> getUserList() throws AccountingApplicationException;
    List<UserDto> getUserByRole(UserRole role) throws AccountingApplicationException;
    List<UserDto> getAllByRoleAndStatus(UserRole role, CompanyStatus status);
    UserDto updateById(String id, UserDto userDto) throws UserNotFoundInSystem;
    UserDto deleteUserById(String id) throws UserNotFoundInSystem;
    List<UserDto> getUserListByCompany(Integer companyId);



}
