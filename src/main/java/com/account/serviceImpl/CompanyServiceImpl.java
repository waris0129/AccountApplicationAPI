package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.CompanyDTO;
import com.account.entity.Company;
import com.account.enums.CompanyStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.ExceptionHandlers;
import com.account.repository.CompanyRepository;
import com.account.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MapperUtility mapperUtility;

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) throws AccountingApplicationException {

        Optional<Company> foundCompany = companyRepository.findByTitle(companyDTO.getTitle());
        if(foundCompany.isPresent())
            throw new AccountingApplicationException("Company is already existed");

        companyDTO.setEnabled(true);
        companyDTO.setCompanyStatus(CompanyStatus.ACTIVE);

        Company company = mapperUtility.convert(companyDTO,new Company());

        Company createCompany = companyRepository.save(company);

        return mapperUtility.convert(createCompany,new CompanyDTO());
    }

    @Override
    public CompanyDTO findByTitle(String title) throws CompanyNotFoundException {

        Optional<Company> foundCompany = companyRepository.findByTitle(title);

        if(foundCompany.isPresent())
                return mapperUtility.convert(foundCompany.get(),new CompanyDTO());

        throw new CompanyNotFoundException("Company not found");
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
