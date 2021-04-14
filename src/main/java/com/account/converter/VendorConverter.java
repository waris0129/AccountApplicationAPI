package com.account.converter;

import com.account.dto.CategoryDTO;
import com.account.dto.VendorDTO;
import com.account.service.CategoryService;
import com.account.service.VendorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class VendorConverter implements Converter<String, VendorDTO> {

    @Autowired
    @Lazy
    private VendorService vendorService;

    @SneakyThrows
    @Override
    public VendorDTO convert(String vendorName) {

        return vendorService.get(vendorName);
    }
}
