package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.VendorDTO;
import com.account.entity.Vendor;
import com.account.enums.VendorStatus;
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
    public VendorDTO save(VendorDTO vendorDTO) {
        // Next: using security context to get User info from token, then passed Company object
        // but currently we need to manually add Company object
        vendorDTO.setEnabled(true);
        vendorDTO.setDeleted(false);
        vendorDTO.setStatus(VendorStatus.ACTIVE);

        Vendor vendor = mapperUtility.convert(vendorDTO,new Vendor());

        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO createdDTO = mapperUtility.convert(savedVendor,new VendorDTO());

        return createdDTO;
    }

    @Override
    public VendorDTO get(String companyName) {
        return null;
    }

    @Override
    public VendorDTO update(String companyName, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO delete(String companyName) {
        return null;
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
