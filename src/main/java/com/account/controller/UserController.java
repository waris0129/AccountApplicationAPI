package com.account.controller;


import com.account.Mapper.MapperUtility;
import com.account.dto.UserDto;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/new")
    public UserDto getEmptyObject(Model model){
        return new UserDto();
    }


    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> saveUser(@RequestBody UserDto userDto) throws AccountingApplicationException {
        UserDto dto = userService.save(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("User is created").data(dto).build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseWrapper> saveUser(@PathVariable("email") String email) throws UserNotFoundInSystem {
        UserDto dto = userService.getUser(email);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("User is found").data(dto).build());
    }

}
