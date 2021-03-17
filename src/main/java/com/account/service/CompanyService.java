package com.account.service;

import com.account.dto.CompanyDTO;

import java.util.List;

public interface CompanyService{

    CompanyDTO save(CompanyDTO companyDTO);
    CompanyDTO findByTitle(String title);
    CompanyDTO update(CompanyDTO companyDTO);
    CompanyDTO delete(CompanyDTO companyDTO);
    List<CompanyDTO> findAllCompanies();



}
