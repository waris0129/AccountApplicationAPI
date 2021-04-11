package com.account.service;

import com.account.dto.UserDto;
import com.account.entity.Company;
import com.account.entity.ConfirmationToken;
import com.account.entity.User;
import com.account.exceptionHandler.InvalidTokenException;

public interface ConfirmationTokenService {

    ConfirmationToken save(ConfirmationToken confirmationToken);
    void sendEmail(User user);
    void sendEmail(Company company);
    ConfirmationToken readByToken(String token) throws InvalidTokenException;
    void delete(ConfirmationToken confirmationToken) throws InvalidTokenException;

}
