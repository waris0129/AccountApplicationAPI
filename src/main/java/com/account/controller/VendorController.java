package com.account.controller;

import com.account.dto.VendorDTO;
import com.account.entity.Vendor;
import com.account.enums.VendorStatus;
import com.account.exceptionHandler.ResponseWrapper;
import com.account.repository.VendorRepository;
import com.account.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;


    @GetMapping("/new")
    public VendorDTO getEmptyObject(){
        return new VendorDTO();
    }


    @PostMapping("/new")
    public ResponseEntity<ResponseWrapper> save(@RequestBody VendorDTO vendorDTO){
        // Next: using security context to get User info from token, then passed Company object
        // but currently we need to manually add Company object
        VendorDTO savedDto = vendorService.save(vendorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.builder().code(HttpStatus.OK.value()).success(true).message("Vendor is created successfully").data(savedDto).build());
    }


}
