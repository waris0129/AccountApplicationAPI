package com.account.service;

import com.account.dto.CompanyDTO;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;

import java.util.List;

public interface CompanyService{

    CompanyDTO save(CompanyDTO companyDTO) throws AccountingApplicationException;
    CompanyDTO findByTitle(String title) throws CompanyNotFoundException;
    CompanyDTO update(CompanyDTO companyDTO);
    CompanyDTO delete(CompanyDTO companyDTO);
    List<CompanyDTO> findAllCompanies();



}
