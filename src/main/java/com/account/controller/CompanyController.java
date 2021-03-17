package com.account.controller;

import com.account.dto.CompanyDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/new")
    public CompanyDTO getEmptyCompanyDTOObject(){
        return new CompanyDTO();
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> saveCompany(@RequestBody CompanyDTO companyDTO, Model model) throws AccountingApplicationException {

        CompanyDTO createdCompanyDTO = companyService.save(companyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Company created successfully").data(createdCompanyDTO).build());
    }

    @GetMapping("get/{title}")
    public ResponseEntity<ResponseWrapper> findCompanyByTitle(@PathVariable("title") String title) throws CompanyNotFoundException {
        CompanyDTO foundCompanyDTO = companyService.findByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Company found successfully").data(foundCompanyDTO).build());
    }






}
