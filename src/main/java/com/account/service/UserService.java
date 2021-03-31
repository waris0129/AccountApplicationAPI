package com.account.service;

import com.account.dto.UserDto;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto) throws AccountingApplicationException;
    UserDto update(String email, UserDto userDto) throws UserNotFoundInSystem, AccountingApplicationException;
    UserDto update(UserDto userDto);
    UserDto getUser(String email) throws UserNotFoundInSystem, AccountingApplicationException;
    UserDto deleteUser(String email) throws UserNotFoundInSystem, AccountingApplicationException;
    List<UserDto> getUserList() throws AccountingApplicationException;
    List<UserDto> getUserByRole(String role) throws AccountingApplicationException;




}
