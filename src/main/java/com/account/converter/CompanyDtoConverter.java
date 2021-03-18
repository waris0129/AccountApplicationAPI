package com.account.converter;

import com.account.dto.CompanyDTO;
import com.account.service.CompanyService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding
public class CompanyDtoConverter implements Converter<String,CompanyDTO> {

    @Autowired
    private CompanyService companyService;

    @SneakyThrows
    @Override
    public CompanyDTO convert(String title) {
        return companyService.findByTitle(title);
    }
}
