package com.account.service;

import com.account.entity.User;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

    public User loadUser(String username) throws InvalidTokenException, UserNotFoundInSystem;
}
