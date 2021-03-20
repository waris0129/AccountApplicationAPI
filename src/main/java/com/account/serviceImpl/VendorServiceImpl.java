package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.CompanyDTO;
import com.account.dto.VendorDTO;
import com.account.entity.Company;
import com.account.entity.Vendor;
import com.account.enums.RegistrationType;
import com.account.enums.VendorStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.VendorRepository;
import com.account.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private MapperUtility mapperUtility;


    @Override
    public VendorDTO save(VendorDTO vendorDTO) throws AccountingApplicationException {
        // Next: using security context to get User info from token, then passed Company object
        // but currently we need to manually add Company object

        Vendor foundVendor = vendorRepository.getByCompanyName(vendorDTO.getCompanyName());

        if (foundVendor !=null)
            throw new AccountingApplicationException("Vendor is already existed in System");

        vendorDTO.setEnabled(true);
        vendorDTO.setDeleted(false);
        vendorDTO.setStatus(VendorStatus.ACTIVE);

        Vendor vendor = mapperUtility.convert(vendorDTO,new Vendor());

        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO createdDTO = mapperUtility.convert(savedVendor,new VendorDTO());

        return createdDTO;
    }

    @Override
    public VendorDTO get(String companyName) throws UserNotFoundInSystem {

        Vendor foundVendor = vendorRepository.getByCompanyName(companyName);

        if(foundVendor == null)
            throw new UserNotFoundInSystem("Vendor not found in system");

        VendorDTO vendorDTO = mapperUtility.convert(foundVendor,new VendorDTO());

        return vendorDTO;
    }

    @Override
    public VendorDTO update(String companyName, VendorDTO vendorDTO) throws UserNotFoundInSystem {

        Vendor foundVendor = vendorRepository.getByCompanyName(companyName);

        if(foundVendor == null)
            throw new UserNotFoundInSystem("Vendor not found in system");

        Integer id = foundVendor.getId();
        Company company = foundVendor.getCompany();
        VendorStatus status = foundVendor.getStatus();
        Boolean enable = foundVendor.getEnabled();
        Boolean deleted = foundVendor.getDeleted();

        Vendor updateVendor = mapperUtility.convert(vendorDTO,new Vendor());

        updateVendor.setCompanyName(companyName);
        updateVendor.setId(id);
        updateVendor.setCompany(company);
        updateVendor.setStatus(status);
        updateVendor.setEnabled(enable);
        updateVendor.setDeleted(deleted);

        Vendor createdVendor = vendorRepository.save(updateVendor);
        VendorDTO createdDTO = mapperUtility.convert(createdVendor,new VendorDTO());

        return createdDTO;
    }

    @Override
    public VendorDTO delete(String companyName) throws UserNotFoundInSystem {

        Vendor foundVendor = vendorRepository.getByCompanyName(companyName);

        if(foundVendor == null)
            throw new UserNotFoundInSystem("");

        foundVendor.setDeleted(true);
        foundVendor.setStatus(VendorStatus.DELETED);

        Vendor deleteVendor = vendorRepository.save(foundVendor);

        VendorDTO dto = mapperUtility.convert(deleteVendor,new VendorDTO());

        return dto;
    }

    @Override
    public List<VendorDTO> getAllVendorList() {


        return null;
    }

    @Override
    public List<VendorDTO> getAllVendorByStatus(String status) {
        return null;
    }
}
