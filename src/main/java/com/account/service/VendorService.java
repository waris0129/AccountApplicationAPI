package com.account.service;

import com.account.dto.VendorDTO;

import java.util.List;

public interface VendorService {

    VendorDTO save(VendorDTO vendorDTO);
    VendorDTO get(String companyName);
    VendorDTO update(String companyName, VendorDTO vendorDTO);
    VendorDTO delete(String companyName);
    List<VendorDTO> getAllVendorList();
    List<VendorDTO> getAllVendorByStatus(String status);

}
