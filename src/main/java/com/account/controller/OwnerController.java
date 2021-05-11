package com.account.controller;

import com.account.dto.CompanyDTO;
import com.account.dto.RoleDTO;
import com.account.dto.UserDto;
import com.account.entity.Company;
import com.account.entity.ConfirmationToken;
import com.account.entity.User;
import com.account.enums.CompanyStatus;
import com.account.enums.States;
import com.account.enums.UserRole;
import com.account.enums.UserStatus;
import com.account.exceptionHandler.*;
import com.account.service.CompanyService;
import com.account.service.ConfirmationTokenService;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owner")
@PreAuthorize("hasAnyAuthority({'Root','Owner'})")
public class OwnerController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private UserService userService;

    private String error;

    @GetMapping("/company-registration")
    public CompanyDTO getEmptyCompanyDTOObject(Model model){

        return new CompanyDTO();

    }

    @PostMapping("/company-registration")
    public ResponseEntity<ResponseWrapper> saveCompany(@RequestBody CompanyDTO companyDTO, Model model) throws AccountingApplicationException {

        CompanyDTO createdCompanyDTO = companyService.save(companyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Company created successfully").data(createdCompanyDTO).build());
    }


    @GetMapping("/get/company/{title}")
    public ResponseEntity<ResponseWrapper> findCompanyByTitle(@PathVariable("title") String title,Model model) throws CompanyNotFoundException {
        CompanyDTO foundCompanyDTO = companyService.findByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Company found successfully").data(foundCompanyDTO).build());
    }

    @PostMapping("/update/company/{title}")
    public ResponseEntity<ResponseWrapper> updateCompany(@PathVariable("title") String title, @RequestBody CompanyDTO companyDTO, Model model) throws CompanyNotFoundException {
        CompanyDTO updatedCompany = companyService.update(title, companyDTO);
        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Company updated successfully").data(updatedCompany).build());
    }



    @GetMapping("/delete/company/{title}")
    public ResponseEntity<ResponseWrapper> deleteCompany(@PathVariable("title") String title,Model model) throws CompanyNotFoundException {

        CompanyDTO companyDTO = companyService.delete(title);
        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Company deleted successfully").build());

    }

    @GetMapping("/all-companyList")
    public ResponseEntity<ResponseWrapper> getAllCompany(){
        List<CompanyDTO> companyDTOS = companyService.findAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get All Company List successfully").data(companyDTOS).build());
    }




    @GetMapping("/admin-registration")
    public UserDto getAdminObject(UserDto userDto, Model model){
        return new UserDto();
    }



    @PostMapping("/admin-registration")
    public ResponseEntity<ResponseWrapper> saveAdmin(@RequestBody UserDto userDto, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        UserDto dto = userService.save(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Admin is created").data(dto).build());
    }


    @GetMapping("/get/admin/{id}")
    public ResponseEntity<ResponseWrapper> findAdmin(@PathVariable("id") String id,Model model) throws AccountingApplicationException, UserNotFoundInSystem {
        UserDto dto = userService.getUserById(Integer.parseInt(id));

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Admin is found").data(dto).build());
    }

    @PostMapping("/update/admin/{id}")
    public ResponseEntity<ResponseWrapper> updateAdmin(@PathVariable("id") String id, @RequestBody UserDto userDto, Model model) throws UserNotFoundInSystem {

        UserDto dto = userService.updateById(id,userDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Admin is updated successfully").data(dto).build());
    }



    @GetMapping("/delete/admin/{id}")
    public ResponseEntity<ResponseWrapper> deleteAdmin(@PathVariable("id") String id) throws UserNotFoundInSystem {

        UserDto userDto = userService.deleteUserById(id);

        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Admin is deleted successfully").build());
    }


    @GetMapping("/all-adminList")
    public ResponseEntity<ResponseWrapper> getUserList(){
        List<UserDto> adminDtoList = userService.getAllByRoleAndStatus(UserRole.Admin, CompanyStatus.ACTIVE);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get All User List successfully").data(adminDtoList).build());
    }








}
