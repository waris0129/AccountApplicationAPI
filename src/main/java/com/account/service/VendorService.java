package com.account.service;

import com.account.dto.VendorDTO;
import com.account.entity.Vendor;
import com.account.enums.VendorStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.CompanyNotFoundException;
import com.account.exceptionHandler.UserNotFoundInSystem;

import java.util.List;

public interface VendorService {

    VendorDTO save(VendorDTO vendorDTO) throws AccountingApplicationException, CompanyNotFoundException;
    VendorDTO get(String companyName) throws UserNotFoundInSystem;
    VendorDTO update(String companyName, VendorDTO vendorDTO) throws UserNotFoundInSystem;
    VendorDTO delete(String companyName) throws UserNotFoundInSystem;
    List<VendorDTO> getAllVendorList();
    List<VendorDTO> getAllVendorByStatus(String status) throws AccountingApplicationException;
    List<VendorDTO> getAllActiveVendorByCompany(Integer companyId);

}
