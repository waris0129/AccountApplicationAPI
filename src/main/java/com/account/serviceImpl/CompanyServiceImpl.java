package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.CompanyDTO;
import com.account.entity.Company;
import com.account.enums.CompanyStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.repository.CompanyRepository;
import com.account.service.CompanyService;
import com.account.service.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;


    @Override
    public CompanyDTO findById(Integer id) {
        return mapperUtility.convert(companyRepository.findById(id).get(),new CompanyDTO());
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) throws AccountingApplicationException {

        Optional<Company> foundCompany = companyRepository.findByTitle(companyDTO.getTitle());
        if(foundCompany.isPresent())
            throw new AccountingApplicationException("Company is already existed");

        companyDTO.setEnabled(false);
        companyDTO.setStatus(CompanyStatus.IN_ACTIVE);
        companyDTO.setDeleted(false);

        Company company = mapperUtility.convert(companyDTO,new Company());

        Company createCompany = companyRepository.save(company);

        confirmationTokenService.sendEmail(createCompany);

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
    public CompanyDTO update(String title, CompanyDTO companyDTO) throws CompanyNotFoundException {

        Optional<Company> company = companyRepository.findByTitle(title);
        if(!company.isPresent())
            throw new CompanyNotFoundException("Company not found");

        Integer id = company.get().getId();
        Boolean enable = company.get().getEnabled();
        Boolean deleted = company.get().getDeleted();
        CompanyStatus status = company.get().getStatus();

        Company updatedCompany = mapperUtility.convert(companyDTO,new Company());
        updatedCompany.setId(id);
        updatedCompany.setEnabled(enable);
        updatedCompany.setTitle(title);
        updatedCompany.setDeleted(deleted);
        updatedCompany.setStatus(status);

        Company createdCompany = companyRepository.save(updatedCompany);

        return mapperUtility.convert(createdCompany,new CompanyDTO());
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO) throws CompanyNotFoundException {

        String title = companyDTO.getTitle();

        Optional<Company> company = companyRepository.findByTitle(title);
        if(!company.isPresent())
            throw new CompanyNotFoundException("Company not found");

        Integer id = company.get().getId();
        Boolean enable = company.get().getEnabled();

        Company updatedCompany = mapperUtility.convert(companyDTO,new Company());
        updatedCompany.setId(id);
        updatedCompany.setEnabled(enable);

        Company createdCompany = companyRepository.save(updatedCompany);

        return mapperUtility.convert(createdCompany,new CompanyDTO());
    }

    @Override
    public CompanyDTO delete(String title) throws CompanyNotFoundException {

        Optional<Company> foundCompany = companyRepository.findByTitle(title);

        if(!foundCompany.isPresent())
            throw new CompanyNotFoundException("Company not found");

        Company deleteCompany = foundCompany.get();

        deleteCompany.setStatus(CompanyStatus.DELETED);
        deleteCompany.setTitle(deleteCompany.getId()+"_"+title);
        deleteCompany.setDeleted(true);
        deleteCompany.setEnabled(false);


        Company savedCompany = companyRepository.save(deleteCompany);


        return mapperUtility.convert(savedCompany,new CompanyDTO());
    }

    @Override
    public List<CompanyDTO> findAllCompanies() {

        List<Company> companyList = companyRepository.findAll();

        List<CompanyDTO> companyDTOList = companyList.stream().map(entity-> mapperUtility.convert(entity,new CompanyDTO())).collect(Collectors.toList());

        return companyDTOList;
    }
}
