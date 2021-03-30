package com.account.controller;


import com.account.Mapper.MapperUtility;
import com.account.dto.AuthenticateRequest;
import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.securities.JwtUtil;
import com.account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Authentication Controller",description = "Authenticate API")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MapperUtility mapperUtil;
    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private ConfirmationTokenService confirmationTokenService;

    @PostMapping("/authenticate")
    @Operation(summary = "Login to application")
    public ResponseEntity<ResponseWrapper> doLogin(@RequestBody AuthenticateRequest request) throws InvalidTokenException, UserNotFoundInSystem {

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

//    @GetMapping("/confirmation")
//    @Operation(summary = "Confirm account")
//    public ResponseEntity<ResponseWrapper> confirmUser(@RequestParam String token) throws InvalidTokenException {
//        ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);
//
//        User user = confirmationToken.getUser();
//
//        user.setEnabled(true);
//
//        confirmationToken.setIsDeleted(true);
//
//        confirmationTokenService.save(confirmationToken);
//
//        return ResponseEntity.ok(new ResponseWrapper("User is confirmed",user.getUserName()));
//    }





}
