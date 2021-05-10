package com.account.controller;

import com.account.entity.Company;
import com.account.entity.ConfirmationToken;
import com.account.entity.User;
import com.account.enums.CompanyStatus;
import com.account.enums.UserStatus;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ConfirmationController {

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/confirmation-company")
    public ResponseEntity<ResponseWrapper> confirmCompanyRegister(@RequestParam String token) throws InvalidTokenException{
        ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);

        Company company = confirmationToken.getCompany();

        company.setEnabled(true);
        company.setStatus(CompanyStatus.ACTIVE);

        confirmationToken.setIsDeleted(true);

        confirmationTokenService.save(confirmationToken);

        return ResponseEntity.ok(new ResponseWrapper("Company is confirmed: ",company.getTitle()));
    }



    @GetMapping("/confirmation-user")
    public ResponseEntity<ResponseWrapper> confirmUser(@RequestParam String token) throws InvalidTokenException {

        ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);

        User user = confirmationToken.getUser();

        user.setEnabled(true);
        user.setStatus(UserStatus.ACTIVE);
        confirmationToken.setIsDeleted(true);

        confirmationTokenService.save(confirmationToken);

        return ResponseEntity.ok(new ResponseWrapper("User is confirmed: ",user.getEmail()));
    }


}
