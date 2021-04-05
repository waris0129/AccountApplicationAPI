package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.UserDto;
import com.account.entity.Company;
import com.account.entity.User;
import com.account.enums.UserStatus;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.UserRepository;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public UserDto save(UserDto userDto) throws AccountingApplicationException {

        String email = userDto.getEmail();

        Optional<User> foundUser = userRepository.findUserByEmail(email);
        if(foundUser.isPresent())
            throw new AccountingApplicationException("User is already existed in system");

        User user = mapperUtility.convert(userDto,new User());

        user.setEnabled(false);
        user.setDeleted(false);
        user.setStatus(UserStatus.PEND);
        user.setPassword((user.getPassword()));

        User created = userRepository.save(user);

        UserDto createdDTO = mapperUtility.convert(created,new UserDto());

        return createdDTO;
    }


    @Override
    public UserDto update(String email, UserDto userDto) throws UserNotFoundInSystem, AccountingApplicationException {

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

        Optional<User> foundUser = userRepository.findUserByEmail(email);


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


        List<User> users = userRepository.findAll();

        List<UserDto> userDtoList = users.stream().map(entity->mapperUtility.convert(entity,new UserDto())).collect(Collectors.toList());

        return userDtoList;
    }

    @Override
    public List<UserDto> getUserByRole(String role) throws AccountingApplicationException {


        List<User> users = userRepository.getAllByRole(role);
        List<UserDto> userDtoList = users.stream().map(entity->mapperUtility.convert(entity,new UserDto())).collect(Collectors.toList());


        return userDtoList;
    }


}
