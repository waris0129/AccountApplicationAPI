package com.account.service;

import com.account.entity.User;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityService extends UserDetailsService {

    public User loadUser(String username) throws InvalidTokenException, UserNotFoundInSystem, AccountingApplicationException;

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
