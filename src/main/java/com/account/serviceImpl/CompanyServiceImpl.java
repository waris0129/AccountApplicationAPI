package com.account.serviceImpl;

import com.account.dto.CompanyDTO;
import com.account.repository.CompanyRepository;
import com.account.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        return null;
    }

    @Override
    public CompanyDTO findByTitle(String title) {
        return null;
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO) {
        return null;
    }

    @Override
    public CompanyDTO delete(CompanyDTO companyDTO) {
        return null;
    }

    @Override
    public List<CompanyDTO> findAllCompanies() {
        return null;
    }
}
