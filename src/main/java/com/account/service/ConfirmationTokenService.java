package com.account.service;

import com.account.entity.ConfirmationToken;
import com.account.entity.User;
import com.account.exceptionHandler.InvalidTokenException;

public interface ConfirmationTokenService {

    ConfirmationToken save(ConfirmationToken confirmationToken);
    void sendEmail(User user);
    ConfirmationToken readByToken(String token) throws InvalidTokenException;
    void delete(ConfirmationToken confirmationToken) throws InvalidTokenException;

}
