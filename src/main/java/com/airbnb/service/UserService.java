package com.airbnb.service;

import com.airbnb.dto.LoginDto;
import com.airbnb.dto.PropertyUserDto;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private JWTService jwtService;
    private PropertyUserRepository propertyUserRepository;

    public UserService(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }
    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        PropertyUser user = new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        user.setUserRole(propertyUserDto.getUserRole());
        PropertyUser saveUser = propertyUserRepository.save(user);
        return saveUser;

    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser = propertyUserRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()){
            PropertyUser propertyUser = opUser.get();
            if(BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword())){
                return jwtService.generateToken(propertyUser);
            }
        }
        return null;
    }
}
