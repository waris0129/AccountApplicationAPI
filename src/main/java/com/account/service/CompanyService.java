package com.account.service;

import com.account.dto.CompanyDTO;
import com.account.enums.CompanyStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;

import java.util.List;

public interface CompanyService{

    CompanyDTO save(CompanyDTO companyDTO) throws AccountingApplicationException;
    CompanyDTO findByTitle(String title) throws CompanyNotFoundException;
    CompanyDTO update(String title, CompanyDTO companyDTO) throws CompanyNotFoundException;
    CompanyDTO update(CompanyDTO companyDTO) throws CompanyNotFoundException;
    CompanyDTO delete(String title) throws CompanyNotFoundException;
    List<CompanyDTO> findAllCompanies();
    CompanyDTO findById(Integer id) throws CompanyNotFoundException;
    List<CompanyDTO> findAllCompaniesByStatus(CompanyStatus status);



}
