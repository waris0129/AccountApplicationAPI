package com.account.controller;

import com.account.dto.CompanyDTO;
import com.account.dto.UserDto;
import com.account.entity.Company;
import com.account.entity.ConfirmationToken;
import com.account.entity.User;
import com.account.enums.CompanyStatus;
import com.account.enums.States;
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

    @GetMapping("/company/confirmation")
    public String confirmCompanyRegister(@RequestParam String token) throws InvalidTokenException{
        ConfirmationToken confirmationToken = confirmationTokenService.readByToken(token);

        Company company = confirmationToken.getCompany();

        company.setEnabled(true);
        company.setStatus(CompanyStatus.ACTIVE);

        confirmationToken.setIsDeleted(true);

        confirmationTokenService.save(confirmationToken);

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
    public String getAdminObject(UserDto userDto, Model model){
        UserDto dto = new UserDto();

        model.addAttribute("user",userDto);

        return "owner/admin-registration";
    }



    @PostMapping("/admin-registration")
    public String saveAdmin(UserDto userDto, Model model) throws AccountingApplicationException {
        UserDto dto = userService.save(userDto);

        return "redirect:/owner/admin-registration";
    }



}
