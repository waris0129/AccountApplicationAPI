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
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
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


    @GetMapping("/vendor-registration")
    public String getEmptyCompanyDTOObject(Model model){

        VendorDTO vendorDTO = new VendorDTO();

        List<States>stateList = Arrays.stream(States.values()).collect(Collectors.toList());
        List<RegistrationType>typeList = Arrays.stream(RegistrationType.values()).collect(Collectors.toList());
        List<VendorDTO> vendorList = vendorService.getAllVendorList(getLoginCompanyId());
        //vendor type

        model.addAttribute("company",vendorDTO);
        model.addAttribute("stateList",stateList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("companyList",vendorList);
        //needs add vendor type


        return "manager/vendor-registration";
    }

    @PostMapping("/vendor-registration")
    public String saveCompany(@ModelAttribute ("company") VendorDTO vendorDTO, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        VendorDTO createdCompanyDTO = vendorService.save(vendorDTO);

        return "redirect:/manager/vendor-registration";
    }


    @GetMapping("/get/vendor/{companyName}")
    public String findCompanyByTitle(@PathVariable("companyName") String companyName,Model model) throws UserNotFoundInSystem {
        VendorDTO foundVendor = vendorService.get(companyName);
        List<States>stateList = Arrays.stream(States.values()).collect(Collectors.toList());
        List<RegistrationType>typeList = Arrays.stream(RegistrationType.values()).collect(Collectors.toList());
        List<VendorDTO> vendorList = vendorService.getAllVendorList(getLoginCompanyId());

        model.addAttribute("company",foundVendor);
        model.addAttribute("companyList",vendorList);
        model.addAttribute("stateList",stateList);
        model.addAttribute("typeList",typeList);
        // needs add vendor status
        //needs add

        return "manager/vendor-update";
    }

    @PostMapping("/update/vendor/{companyName}")
    public String updateCompany(@PathVariable("companyName") String companyName, @ModelAttribute ("company") VendorDTO vendorDTO, Model model) throws CompanyNotFoundException, UserNotFoundInSystem {
        VendorDTO vendorDTO1 = vendorService.update(companyName, vendorDTO);

        return "redirect:/manager/vendor-registration";
    }



    @GetMapping("/delete/vendor/{companyName}")
    public String deleteCompany(@PathVariable("companyName") String companyName) throws CompanyNotFoundException, UserNotFoundInSystem {

        VendorDTO vendorDTO1 = vendorService.delete(companyName);

        return "redirect:/manager/vendor-registration";
    }


    @GetMapping("/user-registration")
    public String getUserObject(UserDto userDto, Model model) throws AccountingApplicationException, UserNotFoundInSystem {
        UserDto dto = new UserDto();



        List<UserDto> userDtoList = userService.getUserListByCompany(getLoginCompanyId());
        CompanyDTO companyDTO = null;

        try {
            companyDTO = companyService.findById(getLoginCompanyId());
            userDto.setCompany(companyDTO);
        }catch (CompanyNotFoundException e){
            model.addAttribute("errorMessage",e.getMessage());
        }


        model.addAttribute("user",userDto);
        model.addAttribute("userList",userDtoList);
        model.addAttribute("company",companyDTO);


        return "manager/user-registration";
    }



    @PostMapping("/user-registration")
    public String saveUser(UserDto userDto, Model model) throws AccountingApplicationException, CompanyNotFoundException {

        userDto.setCompany(companyService.findById(getLoginCompanyId()));

        UserDto dto = userService.save(userDto);

        return "redirect:/manager/user-registration";
    }


    @GetMapping("/get/user/{id}")
    public String findAdmin(@PathVariable("id") String id,Model model) throws AccountingApplicationException, CompanyNotFoundException {
        UserDto userDto = userService.getUserById(Integer.parseInt(id));
        List<UserDto> userDtoList = userService.getUserListByCompany(getLoginCompanyId());
        CompanyDTO companyDTO = null;

        try {
            companyDTO = companyService.findById(getLoginCompanyId());
            userDto.setCompany(companyDTO);
        }catch (CompanyNotFoundException e){
            model.addAttribute("errorMessage",e.getMessage());
        }

        model.addAttribute("user",userDto);
        model.addAttribute("company",companyDTO);
        model.addAttribute("userList",userDtoList);

        return "manager/user-update";
    }

    @PostMapping("/update/user/{id}")
    public String updateAdmin(@PathVariable("id") String id, @ModelAttribute ("user") UserDto userDto, Model model) throws UserNotFoundInSystem, CompanyNotFoundException {

        userDto.setCompany(companyService.findById(getLoginCompanyId()));

        UserDto dto = userService.updateById(id,userDto);


        return "redirect:/manager/user-registration";
    }



    @GetMapping("/delete/user/{id}")
    public String deleteAdmin(@PathVariable("id") String id) throws UserNotFoundInSystem {

        UserDto userDto = userService.deleteUserById(id);

        return "redirect:/manager/user-registration";
    }








}
