package com.example.service;


import com.example.dto.user.LoginDto;
import com.example.dto.user.UserDto;
import com.example.dto.user.UserReq;
import com.example.exception.DuplicateKeyException;
import com.example.exception.NotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRep;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRep userRep;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(UserReq t) {
        if(userRep.existsByUsername(t.getUsername())) {
            throw new DuplicateKeyException("Username already exists");
        }
        if(userRep.existsByEmail(t.getEmail())) {
            throw new DuplicateKeyException("Email already exists");
        }
        if(userRep.existsByPhone(t.getPhone())) {
            throw  new DuplicateKeyException("Phone already exists");
        }
        User user = userMapper.mapTo(t);
        User newUser = userRep.save(user);
        return userMapper.mapToDto(newUser);
    }

    @Override
    public LoginDto login(String username, String password) {
        User user  = userRep.findByUsername(username).orElseThrow(() -> new NotFoundException("account does not exist"));
        if(!passwordEncoder.matches(password, user.getPassword())) {
             throw new NotFoundException("Password does not match");
        }
        return new LoginDto(user.getId(), user.getRole());
    }
}
