package com.account.converter;

import com.account.dto.RoleDTO;
import com.account.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class RoleConverter implements Converter<String, RoleDTO> {

    @Autowired
    private RoleService roleService;



    @Override
    public RoleDTO convert(String id) {

        return roleService.findById(Integer.parseInt(id));
    }
}
