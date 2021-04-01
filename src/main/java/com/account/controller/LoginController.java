package com.account.controller;


import com.account.Mapper.MapperUtility;
import com.account.dto.AuthenticateRequest;
import com.account.dto.UserDto;
import com.account.entity.BaseEntity;
import com.account.entity.ConfirmationToken;
import com.account.entity.User;
import com.account.enums.UserStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.securities.JwtUtil;
import com.account.service.ConfirmationTokenService;
import com.account.service.SecurityService;
import com.account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityListeners;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


@RestController
@Tag(name = "Authentication Controller",description = "Authenticate API")
public class LoginController{

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MapperUtility mapperUtil;
    @Autowired
    private JwtUtil jwtUtil;

    public static String role;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private SecurityService securityService;

    @PostMapping("/authenticate")
    @Operation(summary = "Login to application")
    public ResponseEntity<ResponseWrapper> doLogin(@RequestBody AuthenticateRequest request) throws InvalidTokenException, UserNotFoundInSystem, AccountingApplicationException {

        String email = request.getEmail();
        String password = request.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(authenticationToken);

        UserDto userDTO = userService.getUser(email);

        User userEntity = mapperUtil.convert(userDTO, new User());

        if(userEntity.getEnabled()==false)
            throw new InvalidTokenException("User is not verified yet");


        String token = jwtUtil.generateToken(userEntity);

        return ResponseEntity.ok(new ResponseWrapper("user is successfully login",token));


    }

    @GetMapping("/confirmation")
    public ResponseEntity<ResponseWrapper> confirmUser(@RequestParam String token) throws InvalidTokenException, UserNotFoundInSystem, AccountingApplicationException {
        ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);

        User user = confirmationToken.getUser();

        user.setEnabled(true);
        user.setStatus(UserStatus.ACTIVE);

        confirmationToken.setIsDeleted(true);

        role = user.getRole().getName();

        confirmationTokenService.save(confirmationToken);

        return ResponseEntity.ok(new ResponseWrapper("User is confirmed",user.getEmail()));
    }


    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity){
        baseEntity.setUpdatedTime(LocalDateTime.now());
        baseEntity.setUpdatedBy(role+" from ConfirmationToken");

    }



}
