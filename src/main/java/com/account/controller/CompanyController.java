package com.account.controller;

import com.account.dto.CompanyDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@PreAuthorize("hasAuthority('Root')")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/new")
    public CompanyDTO getEmptyCompanyDTOObject(Model model){
        return new CompanyDTO();
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> saveCompany(@RequestBody CompanyDTO companyDTO, Model model) throws AccountingApplicationException {

        CompanyDTO createdCompanyDTO = companyService.save(companyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.CREATED.value()).success(true).message("Company created successfully").data(createdCompanyDTO).build());
    }

    @GetMapping("get/{title}")
    public ResponseEntity<ResponseWrapper> findCompanyByTitle(@PathVariable("title") String title,Model model) throws CompanyNotFoundException {
        CompanyDTO foundCompanyDTO = companyService.findByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Company found successfully").data(foundCompanyDTO).build());
    }

    @PutMapping("/update/{title}")
    public ResponseEntity<ResponseWrapper> updateCompany(@PathVariable("title") String title, @RequestBody CompanyDTO companyDTO, Model model) throws CompanyNotFoundException {
        CompanyDTO updatedCompany = companyService.update(title, companyDTO);
        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Company updated successfully").data(updatedCompany).build());
    }



    @DeleteMapping("/delete/{title}")
    public ResponseEntity<ResponseWrapper> deleteCompany(@PathVariable("title") String title) throws CompanyNotFoundException {

        CompanyDTO companyDTO = companyService.delete(title);
        return ResponseEntity.ok(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Company deleted successfully").build());

    }

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getAllCompany(){
        List<CompanyDTO> companyDTOS = companyService.findAllCompanies();
        return ResponseEntity.status(HttpStatus.OK).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Get All Company List successfully").data(companyDTOS).build());
    }


}
