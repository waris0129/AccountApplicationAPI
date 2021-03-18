package com.account.serviceImpl;

import com.account.Mapper.MapperUtility;
import com.account.dto.UserDto;
import com.account.entity.User;
import com.account.exceptionHandler.AccountingApplicationException;
import com.account.exceptionHandler.UserNotFoundInSystem;
import com.account.repository.UserRepository;
import com.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        user.setEnabled(true);
        user.setDeleted(false);

        User created = userRepository.save(user);

        UserDto createdDTO = mapperUtility.convert(created,new UserDto());

        return createdDTO;
    }

    @Override
    public UserDto update(String email, UserDto userDto) {
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getUser(String email) throws UserNotFoundInSystem {

        Optional<User> foundUser = userRepository.findUserByEmail(email);

        if(!foundUser.isPresent())
            throw new UserNotFoundInSystem("");

        UserDto userDto = mapperUtility.convert(foundUser.get(),new UserDto());

        return userDto;
    }

    @Override
    public UserDto deleteUser(String email) {
        return null;
    }

    @Override
    public List<UserDto> getUserList() {
        return null;
    }

    @Override
    public List<UserDto> getUserByRole(String role) {
        return null;
    }
}
