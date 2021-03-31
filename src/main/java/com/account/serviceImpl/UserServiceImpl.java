package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.UserDto;
import com.account.entity.Company;
import com.account.entity.User;
import com.account.enums.UserRole;
import com.account.enums.UserStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.UserRepository;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapperUtility mapperUtility;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto save(UserDto userDto) throws AccountingApplicationException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        checkValidRole(role, userDto,"add");

        String email = userDto.getEmail();

        Optional<User> foundUser = userRepository.findUserByEmail(email);
        if(foundUser.isPresent())
            throw new AccountingApplicationException("User is already existed in system");

        User user = mapperUtility.convert(userDto,new User());

        setupCompanyByRole(authentication, user);

        user.setEnabled(true);
        user.setDeleted(false);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User created = userRepository.save(user);

        UserDto createdDTO = mapperUtility.convert(created,new UserDto());

        return createdDTO;
    }

    private void setupCompanyByRole(Authentication authentication,User user){
        String role = authentication.getAuthorities().toArray()[0].toString();
        if(role.equals("Admin")){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user1 = userRepository.findUserByEmail(userDetails.getUsername()).get();
            Company company = user1.getCompany();
            user.setCompany(company);
        }
    }

    @Override
    public UserDto update(String email, UserDto userDto) throws UserNotFoundInSystem, AccountingApplicationException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        checkValidUpdate(role,userDto);

        Optional<User> foundUser = userRepository.findUserByEmail(email);

        if(!foundUser.isPresent())
            throw new UserNotFoundInSystem("");

        User user = foundUser.get();
        Company company = user.getCompany();
        Boolean enable = user.getEnabled();
        Boolean deleted = user.getDeleted();
        Integer userId = user.getId();
        UserStatus status = user.getStatus();

        User updateUser = mapperUtility.convert(userDto,new User());
        updateUser.setCompany(company);
        updateUser.setEnabled(enable);
        updateUser.setDeleted(deleted);
        updateUser.setId(userId);
        updateUser.setEmail(email);
        updateUser.setStatus(status);

        User savedUser = userRepository.save(updateUser);

        UserDto updatedDTO = mapperUtility.convert(savedUser,new UserDto());

        return updatedDTO;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getUser(String email) throws UserNotFoundInSystem, AccountingApplicationException {

        Optional<User> foundUser = userRepository.findUserByEmail(email);

        if(!foundUser.isPresent())
            throw new UserNotFoundInSystem("");

        UserDto userDto = mapperUtility.convert(foundUser.get(),new UserDto());

        return userDto;
    }

    @Override
    public UserDto deleteUser(String email) throws UserNotFoundInSystem, AccountingApplicationException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        Optional<User> foundUser = userRepository.findUserByEmail(email);

        checkValidRole(role, mapperUtility.convert(foundUser.get(),new UserDto()),"delete");

        if(!foundUser.isPresent())
            throw new UserNotFoundInSystem("");

        User user = foundUser.get();
        user.setDeleted(true);
        user.setStatus(UserStatus.DELETED);
        user.setEmail(user.getId()+"_"+email);

        User deletedUser = userRepository.save(user);

        UserDto deletedUserDTO = mapperUtility.convert(deletedUser,new UserDto());

        return deletedUserDTO;
    }

    @Override
    public List<UserDto> getUserList() throws AccountingApplicationException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().toArray()[0].toString();

        List<User> users = userRepository.findAll();

        List<UserDto> userDtoList = users.stream().map(entity->mapperUtility.convert(entity,new UserDto())).collect(Collectors.toList());

        return getValidUserList(role,userDtoList);
    }

    @Override
    public List<UserDto> getUserByRole(String role) throws AccountingApplicationException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().toArray()[0].toString();

        List<User> users = userRepository.getAllByRole(role);
        List<UserDto> userDtoList = users.stream().map(entity->mapperUtility.convert(entity,new UserDto())).collect(Collectors.toList());


        return getValidUserList(userRole,userDtoList);
    }




    private void checkValidRole(String role,UserDto userDto,String action) throws AccountingApplicationException {
        if(role.equals("Root")){
            if(!userDto.getRole().getName().equals("Admin"))
                throw new AccountingApplicationException("Root user is only allowed to "+action+" Admin User");
        }

        if(role.equals("Admin")){
            if(userDto.getRole().getName().equals("Root"))
                throw new AccountingApplicationException("Admin user is not allowed to "+action+" Root User");
            if(userDto.getRole().getName().equals("Admin"))
                throw new AccountingApplicationException("Admin user is not allowed to "+action+" Admin User");
        }

    }


    private void checkValidUpdate(String role,UserDto userDto) throws AccountingApplicationException {

        if(userDto.getRole().getName().equals("Root"))
            throw new AccountingApplicationException("No one is allowed to create Root User");

        if(role.equals("Root")){
            if(!userDto.getRole().getName().equals("Admin"))
                throw new AccountingApplicationException("Root user is only allowed to update Admin User");
        }

        if(role.equals("Admin")){
            if(userDto.getRole().getName().equals("Root"))
                throw new AccountingApplicationException("Admin user is not allowed to update Root User");
        }

    }

    private void checkValidGet(String role,UserDto userDto) throws AccountingApplicationException {

        if(role.equals("Root")){
            if(!userDto.getRole().getName().equals("Admin"))
                throw new AccountingApplicationException("Root user is only allowed to get Admin User");
        }

        if(role.equals("Admin")){
            if(userDto.getRole().getName().equals("Root"))
                throw new AccountingApplicationException("Admin user is not allowed to get Root User");
        }

    }


    private List<UserDto> getValidUserList(String role,List<UserDto> userDtoList) throws AccountingApplicationException {

        List<UserDto> list = new ArrayList<>();

        if(role.equals("Root")) {
            list = userDtoList.stream().filter(p -> p.getRole().getName().equals("Admin")).collect(Collectors.toList());
        }else if(role.equals("Admin")){
            list = userDtoList.stream().filter(p->!p.getRole().getName().equals("Root")).collect(Collectors.toList());
        }else {
            throw new AccountingApplicationException("only Root and Admin users are allowed to get all user list");
        }

        return list;
    }


}
