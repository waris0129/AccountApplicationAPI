package com.account.controller;

import com.account.dto.CompanyDTO;
import com.account.dto.UserDto;
import com.account.dto.VendorDTO;
import com.account.enums.CompanyStatus;
import com.account.enums.RegistrationType;
import com.account.enums.States;
import com.account.enums.UserRole;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private CompanyService companyService;
    private VendorService vendorService;
    private UserService userService;

    public ManagerController(CompanyService companyService, VendorService vendorService, UserService userService) {
        this.companyService = companyService;
        this.vendorService = vendorService;
        this.userService = userService;
    }


    private Integer getLoginCompanyId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto securityUser  = null;
        try {
            securityUser = this.userService.getUser(username);
        } catch (UserNotFoundInSystem userNotFoundInSystem) {
            userNotFoundInSystem.printStackTrace();
        } catch (AccountingApplicationException e) {
            e.printStackTrace();
        }
        Integer id =  securityUser.getCompany().getId();

        return id;
    }


    // CRUD Manager create Vendor
    @PreAuthorize("hasAnyAuthority({'Manager'})")
    @GetMapping("/vendor-registration")
    public VendorDTO getEmptyCompanyDTOObject(Model model) throws CompanyNotFoundException {

        VendorDTO vendorDTO = new VendorDTO();

        vendorDTO.setCompany(companyService.findById(getLoginCompanyId()));

        return vendorDTO;
    }


    @PreAuthorize("hasAnyAuthority({'Manager'})")
    @PostMapping("/vendor-registration")
    public ResponseEntity<ResponseWrapper> saveCompany(@RequestBody VendorDTO vendorDTO, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        vendorDTO.setCompany(companyService.findById(getLoginCompanyId()));
        VendorDTO createdCompanyDTO = vendorService.save(vendorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Vendor is created").data(createdCompanyDTO).build());
    }


    @PreAuthorize("hasAnyAuthority({'Manager'})")
    @GetMapping("/get/vendor/{companyName}")
    public ResponseEntity<ResponseWrapper> findCompanyByTitle(@PathVariable("companyName") String companyName,Model model) throws UserNotFoundInSystem {
        VendorDTO foundVendor = vendorService.get(companyName);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Vendor is found").data(foundVendor).build());
    }


    @PreAuthorize("hasAnyAuthority({'Manager'})")
    @PostMapping("/update/vendor/{companyName}")
    public ResponseEntity<ResponseWrapper> updateCompany(@PathVariable("companyName") String companyName, @RequestBody VendorDTO vendorDTO, Model model) throws CompanyNotFoundException, UserNotFoundInSystem {
        VendorDTO vendorDTO1 = vendorService.update(companyName, vendorDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Vendor is updated").data(vendorDTO1).build());
    }


    @PreAuthorize("hasAnyAuthority({'Manager'})")
    @GetMapping("/delete/vendor/{companyName}")
    public ResponseEntity<ResponseWrapper> deleteCompany(@PathVariable("companyName") String companyName) throws CompanyNotFoundException, UserNotFoundInSystem {

        VendorDTO vendorDTO1 = vendorService.delete(companyName);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Vendor is deleted").data(vendorDTO1.getCompanyName()).build());
    }


    @PreAuthorize("hasAnyAuthority({'Manager'})")
    @GetMapping("/all-vendorList")
    public ResponseEntity<ResponseWrapper> getVendorList(){
        List<VendorDTO> vendorDTOS = vendorService.getAllVendorList(getLoginCompanyId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get All Vendor List successfully").data(vendorDTOS).build());
    }



    // CRUD: Admin Create User
    @PreAuthorize("hasAnyAuthority({'Admin'})")
    @GetMapping("/user-registration")
    public UserDto getUserObject(UserDto userDto, Model model) throws AccountingApplicationException, UserNotFoundInSystem, CompanyNotFoundException {
        UserDto dto = new UserDto();

        dto.setCompany(companyService.findById(getLoginCompanyId()));

        return dto;
    }

    @PreAuthorize("hasAnyAuthority({'Admin'})")
    @PostMapping("/user-registration")
    public ResponseEntity<ResponseWrapper> saveUser(@RequestBody UserDto userDto, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        userDto.setCompany(companyService.findById(getLoginCompanyId()));

        UserDto dto = userService.save(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("User is created").data(dto).build());
    }

    @PreAuthorize("hasAnyAuthority({'Admin'})")
    @GetMapping("/get/user/{id}")
    public ResponseEntity<ResponseWrapper> findAdmin(@PathVariable("id") String id,Model model) throws UserNotFoundInSystem, CompanyNotFoundException {
        UserDto userDto = userService.getUserById(Integer.parseInt(id));

        CompanyDTO companyDTO = companyService.findById(getLoginCompanyId());
        userDto.setCompany(companyDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("User is found").data(userDto).build());
    }

    @PreAuthorize("hasAnyAuthority({'Admin'})")
    @PostMapping("/update/user/{id}")
    public ResponseEntity<ResponseWrapper> updateAdmin(@PathVariable("id") String id, @RequestBody UserDto userDto, Model model) throws UserNotFoundInSystem, CompanyNotFoundException {

        userDto.setCompany(companyService.findById(getLoginCompanyId()));

        UserDto dto = userService.updateById(id,userDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("User is updated successfully").data(dto).build());
    }


    @PreAuthorize("hasAnyAuthority({'Admin'})")
    @GetMapping("/delete/user/{id}")
    public ResponseEntity<ResponseWrapper> deleteAdmin(@PathVariable("id") String id) throws UserNotFoundInSystem {

        UserDto userDto = userService.deleteUserById(id);

        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("User is deleted successfully").build());
    }

    @PreAuthorize("hasAnyAuthority({'Admin'})")
    @GetMapping("/all-userList")
    public ResponseEntity<ResponseWrapper> getUserList(){
        List<UserDto> userDtoList = userService.getUserListByCompany(getLoginCompanyId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get All User List successfully").data(userDtoList).build());
    }









}
