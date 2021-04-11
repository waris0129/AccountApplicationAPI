package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.RoleDTO;
import com.account.entity.Role;
import com.account.repository.RoleRepository;
import com.account.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {


    private MapperUtility mapperUtility;
    private RoleRepository roleRepository;

    public RoleServiceImpl(MapperUtility mapperUtility, @Lazy RoleRepository roleRepository) {
        this.mapperUtility = mapperUtility;
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO findById(Integer id) {

        Role role = roleRepository.findById(id).get();

        return mapperUtility.convert(role,new RoleDTO());
    }


}
