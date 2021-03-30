package com.account.serviceImpl;


import com.account.Mapper.MapperUtility;
import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.exceptionHandler.InvalidTokenException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.service.SecurityService;
import com.account.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserService userService;
    @Autowired
    private MapperUtility mapperUtil;

    @Override
    public User loadUser(String email) throws InvalidTokenException, UserNotFoundInSystem {
        UserDto user =  userService.getUser(email);
        return mapperUtil.convert(user,new User());
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDto userDTO = null;
        userDTO = userService.getUser(email);

        if(userDTO==null)
            throw new UsernameNotFoundException("user is not found");

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userDTO.getId().toString(),
                userDTO.getPassword(),
                getAuthorityList(userDTO)
        );


        return userDetails;
    }


    private Collection<? extends GrantedAuthority> getAuthorityList(UserDto userDTO){

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userDTO.getRole().getName());

        grantedAuthorities.add(grantedAuthority);

        return grantedAuthorities;


    }



}
