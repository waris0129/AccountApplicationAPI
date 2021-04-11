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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private UserService userService;

    @GetMapping("/company-registration")
    public String getEmptyCompanyDTOObject(Model model){

        CompanyDTO companyDTO = new CompanyDTO();

        List<States>stateList = Arrays.stream(States.values()).collect(Collectors.toList());
        List<CompanyDTO> companyDTOList = companyService.findAllCompanies();


        model.addAttribute("company",companyDTO);
        model.addAttribute("stateList",stateList);
        model.addAttribute("companyList",companyDTOList);



        return "owner/company-registration";
    }

    @PostMapping("/company-registration")
    public String saveCompany(@ModelAttribute ("company") CompanyDTO companyDTO, Model model) throws AccountingApplicationException {

        CompanyDTO createdCompanyDTO = companyService.save(companyDTO);

        return "redirect:/owner/company-registration";
    }


    @GetMapping("/get/company/{title}")
    public String findCompanyByTitle(@PathVariable("title") String title,Model model) throws CompanyNotFoundException {
        CompanyDTO foundCompanyDTO = companyService.findByTitle(title);
        List<CompanyDTO> companyDTOList = companyService.findAllCompanies();
        List<States>stateList = Arrays.stream(States.values()).collect(Collectors.toList());

        model.addAttribute("company",companyService.findByTitle(title));
        model.addAttribute("companyList",companyDTOList);
        model.addAttribute("stateList",stateList);

        return "owner/company-update";
    }

    @PostMapping("/update/company/{title}")
    public String updateCompany(@PathVariable("title") String title, @ModelAttribute ("company") CompanyDTO companyDTO, Model model) throws CompanyNotFoundException {
        CompanyDTO updatedCompany = companyService.update(title, companyDTO);

        return "redirect:/owner/company-registration";
    }



    @GetMapping("/delete/company/{title}")
    public String deleteCompany(@PathVariable("title") String title) throws CompanyNotFoundException {

        CompanyDTO companyDTO = companyService.delete(title);

        return "redirect:/owner/company-registration";
    }




    @GetMapping("/admin-registration")
    public String getAdminObject(UserDto userDto, Model model) throws AccountingApplicationException {
        UserDto dto = new UserDto();
        List<UserDto> userDtoList = userService.getUserByRole(UserRole.Admin);
        List<CompanyDTO> companyDTOList = companyService.findAllCompaniesByStatus(CompanyStatus.ACTIVE);

        model.addAttribute("user",userDto);
        model.addAttribute("companyList",companyDTOList);
        model.addAttribute("userList",userDtoList);


        return "owner/admin-registration";
    }



    @PostMapping("/admin-registration")
    public String saveAdmin(UserDto userDto, Model model) throws AccountingApplicationException {
        UserDto dto = userService.save(userDto);

        return "redirect:/owner/admin-registration";
    }


    @GetMapping("/get/admin/{id}")
    public String findAdmin(@PathVariable("id") String id,Model model) throws AccountingApplicationException {
        UserDto userDto = userService.getUserById(Integer.parseInt(id));
        List<UserDto> userDtoList = userService.getUserByRole(UserRole.Admin);
        List<CompanyDTO> companyDTOList = companyService.findAllCompaniesByStatus(CompanyStatus.ACTIVE);


        model.addAttribute("user",userDto);
        model.addAttribute("companyList",companyDTOList);
        model.addAttribute("userList",userDtoList);

        return "owner/admin-update";
    }

    @PostMapping("/update/admin/{id}")
    public String updateAdmin(@PathVariable("id") String id, @ModelAttribute ("user") UserDto userDto, Model model) throws UserNotFoundInSystem {

        UserDto dto = userService.updateById(id,userDto);


        return "redirect:/owner/admin-registration";
    }



    @GetMapping("/delete/admin/{id}")
    public String deleteAdmin(@PathVariable("id") String id) throws UserNotFoundInSystem {

        UserDto userDto = userService.deleteUserById(id);

        return "redirect:/owner/admin-registration";
    }








}
