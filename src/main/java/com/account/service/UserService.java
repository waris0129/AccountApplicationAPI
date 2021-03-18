package com.account.service;

import com.account.dto.UserDto;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto) throws AccountingApplicationException;
    UserDto update(String email, UserDto userDto);
    UserDto update(UserDto userDto);
    UserDto getUser(String email) throws UserNotFoundInSystem;
    UserDto deleteUser(String email);
    List<UserDto> getUserList();
    List<UserDto> getUserByRole(String role);




}
