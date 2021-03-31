package com.account.controller;


import com.account.Mapper.MapperUtility;
import com.account.dto.CompanyDTO;
import com.account.dto.UserDto;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority({'Root','Admin'})")
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
    public ResponseEntity<ResponseWrapper> saveUser(@PathVariable("email") String email) throws UserNotFoundInSystem, AccountingApplicationException {
        UserDto dto = userService.getUser(email);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("User is found").data(dto).build());
    }

    @PutMapping("/{email}")
    public ResponseEntity<ResponseWrapper> updateUser(@PathVariable("email") String email, @RequestBody UserDto userDto) throws UserNotFoundInSystem, AccountingApplicationException {
        UserDto dto = userService.update(email,userDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("User is updated successfully").data(dto).build());
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable String email) throws UserNotFoundInSystem, AccountingApplicationException {
        UserDto dto = userService.deleteUser(email);
        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("User is deleted successfully").build());
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> getAllUsers() throws AccountingApplicationException {
        List<UserDto> userDtoList = userService.getUserList();

        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get all User list successfully").data(userDtoList).build());
    }


    @GetMapping("/all/{role}")
    public ResponseEntity<ResponseWrapper> getAllUsersByRole(@PathVariable("role") String role) throws AccountingApplicationException {
        List<UserDto> userDtoList = userService.getUserByRole(role);

        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get all User list by Role successfully").data(userDtoList).build());
    }




}
